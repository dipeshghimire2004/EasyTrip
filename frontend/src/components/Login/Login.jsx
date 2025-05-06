import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import api, { setAuthToken } from "../API/Api";

// Helper function to decode a JWT payload
function parseJwt(token) {
  try {
    const base64Url = token.split('.')[1]; // JWT token structure: header.payload.signature
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    // Decode the base64 string
    const jsonPayload = decodeURIComponent(
      atob(base64)
        .split('')
        .map((c) => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
        .join('')
    );
    return JSON.parse(jsonPayload);
  } catch (e) {
    console.error("Failed to parse JWT:", e);
    return {};
  }
}

export default function Login({ role = "CLIENT", setUserRole }) {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();
  const navigate = useNavigate();

  const onSubmit = (data) => {
    const loginData = {
      email: data.email,
      password: data.password,
    };

    alert(`Logging in as ${role} with email: ${data.email}`);
    setUserRole(role);

    api.post("/api/auth/login", loginData)
      .then((response) => {
        const { accessToken, refreshToken } = response.data;
        setAuthToken(accessToken);
        localStorage.setItem("accessToken", accessToken);

        // Decode the JWT access token using the helper function
        const decodedToken = parseJwt(accessToken);
        // console.log(decodedToken);
        const tokenRole = (decodedToken.roles && decodedToken.roles[0]) || role;
        console.log("Extracted Role:", tokenRole);
        setUserRole(tokenRole);
        // Save role to localStorage
        localStorage.setItem("userRole", tokenRole);

        // Set cookie without secure & with lax
        document.cookie = `refreshToken=${refreshToken}; path=/; max-age=${7 * 24 * 60 * 60}; samesite=lax`;

        // Navigate after short delay to ensure cookie is saved
        setTimeout(() => {
          if (tokenRole === "ADMIN") {
            navigate("/ADMIN/panel");
          } else if (tokenRole === "HOTEL_MANAGER") {
            navigate("/HOTEL_MANAGER/addGuestHouse");
          } else {
            navigate("/booking");
          }
        }, 100);
      })
      .catch((error) => {
        console.error("Login error:", error.response?.data || error.message);
      });
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100 p-4">
      <div className="flex flex-col md:flex-row w-full max-w-4xl bg-white rounded-2xl shadow-lg overflow-hidden">
        
        {/* Form Section */}
        <div className="w-full md:w-1/2 p-8">
          <h2 className="text-2xl font-bold text-gray-800 text-center">
            {role.charAt(0).toUpperCase() + role.slice(1)} Login
          </h2>
          <form className="mt-6" onSubmit={handleSubmit(onSubmit)}>
            <div className="mb-4">
              <label className="block text-sm font-medium text-gray-700">Your Email</label>
              <input
                type="email"
                {...register("email", {
                  required: "Email is required",
                  pattern: {
                    value: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
                    message: "Invalid email address",
                  },
                })}
                className="w-full p-3 border rounded-lg mt-1 focus:ring-2 focus:ring-green-500"
              />
              {errors.email && (
                <p className="text-red-500 text-sm">{errors.email.message}</p>
              )}
            </div>
            <div className="mb-6">
              <label className="block text-sm font-medium text-gray-700">Password</label>
              <input
                type="password"
                {...register("password", {
                  required: "Password is required",
                  pattern: {
                    value: /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/,
                    message: "Must be at least 6 characters and contain a number",
                  },
                })}
                className="w-full p-3 border rounded-lg mt-1 focus:ring-2 focus:ring-green-500"
              />
              {errors.password && (
                <p className="text-red-500 text-sm">{errors.password.message}</p>
              )}
            </div>
            <button
              type="submit"
              className="w-full bg-green-600 text-white py-3 rounded-lg font-semibold hover:bg-green-700 transition-all"
            >
              Login
            </button>
          </form>
          {role !== "ADMIN" && (
            <p className="mt-6 text-sm text-center">
              Don’t have an account?{" "}
              <a href={`/${role}/signup`} className="text-green-600 font-semibold hover:underline">
                Sign Up Here
              </a>
            </p>
          )}
        </div>

        {/* Welcome Section */}
        <div className="w-full md:w-1/2 bg-gradient-to-r from-black to-gray-800 text-white flex flex-col justify-center p-8">
          <div className="bg-white bg-opacity-30 backdrop-blur-lg p-6 rounded-2xl">
            <h2 className="text-3xl font-bold">Welcome Back</h2>
            <p className="mt-3 text-sm opacity-90">
              Login to continue and explore all features available to you.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}
