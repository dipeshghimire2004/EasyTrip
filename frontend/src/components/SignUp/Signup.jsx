import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import api from "../API/api";

export default function Signup({ role = "CLIENT" }) {
  const {
    register,
    handleSubmit,
    watch,
    formState: { errors },
  } = useForm();
  const navigate = useNavigate();
  const password = watch("password");

  const onSubmit = (data) => {
    const signupData = {
      name: data.name,
      email: data.email,
      password: data.password,
      confirmPassword: data.confirmPassword,
      registrationDate: new Date().toISOString(),
      role: role,
    };

    console.log(signupData);

    api
      .post("api/auth/register", signupData)
      .then((response) => {
        console.log("Signup successful:", response.data);
        navigate(`/${role}/login`);
      })
      .catch((error) => {
        console.error("Signup error:", error.response?.data || error.message);
      });
  };
  

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100 px-4 sm:px-6 lg:px-8">
      <div className="flex flex-col md:flex-row w-full max-w-4xl bg-white rounded-2xl overflow-hidden shadow-lg">
        {/* Left Section */}
        <div className="w-full md:w-1/2 bg-gradient-to-r from-black to-gray-800 text-white flex flex-col justify-center p-6 sm:p-8">
          <div className="bg-white bg-opacity-20 backdrop-blur-md p-4 sm:p-6 rounded-2xl">
            <h2 className="text-xl sm:text-2xl font-bold">Welcome Back</h2>
            <p className="mt-2 text-sm sm:text-base opacity-90">
              Login to continue and explore all features available to you.
            </p>
          </div>
        </div>

        {/* Right Section */}
        <div className="w-full md:w-1/2 p-6 sm:p-8 flex flex-col justify-center">
          <h2 className="text-xl sm:text-2xl font-bold text-gray-800 text-center">
            {role.charAt(0).toUpperCase() + role.slice(1)} Signup
          </h2>
          <form className="mt-6" onSubmit={handleSubmit(onSubmit)}>
            <div className="mb-4">
              <label className="block text-sm font-medium text-gray-700">Full Name</label>
              <input
                type="text"
                {...register("name", { required: "Name is required" })}
                className="w-full p-2 border rounded-lg mt-1 focus:ring-2 focus:ring-green-500"
              />
              {errors.name && <p className="text-red-500 text-xs mt-1">{errors.name.message}</p>}
            </div>

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
                className="w-full p-2 border rounded-lg mt-1 focus:ring-2 focus:ring-green-500"
              />
              {errors.email && <p className="text-red-500 text-xs mt-1">{errors.email.message}</p>}
            </div>

            <div className="mb-4">
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
                className="w-full p-2 border rounded-lg mt-1 focus:ring-2 focus:ring-green-500"
              />
              {errors.password && <p className="text-red-500 text-xs mt-1">{errors.password.message}</p>}
            </div>

            <div className="mb-6">
              <label className="block text-sm font-medium text-gray-700">Confirm Password</label>
              <input
                type="password"
                {...register("confirmPassword", {
                  required: "Confirm password is required",
                  validate: (value) => value === password || "Passwords do not match",
                })}
                className="w-full p-2 border rounded-lg mt-1 focus:ring-2 focus:ring-green-500"
              />
              {errors.confirmPassword && (
                <p className="text-red-500 text-xs mt-1">{errors.confirmPassword.message}</p>
              )}
            </div>

            <button
              type="submit"
              className="w-full bg-green-600 text-white py-2 rounded-lg font-semibold hover:bg-green-700 transition-all"
            >
              Create Account
            </button>
          </form>
          <p className="mt-4 text-xs text-center">
            Already have an account?{" "}
            <a href={`/${role}/login`} className="text-green-600 font-semibold hover:underline">
              Login Here
            </a>
          </p>
        </div>
      </div>
    </div>
  );
}
