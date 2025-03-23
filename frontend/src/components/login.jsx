import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";

export default function Login() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();
  const navigate = useNavigate();

  const onSubmit = (data) => {
    alert(`Logging in with: ${data.email}`);
    // Add your login logic here.
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100">
      {/* Container with relative positioning and fixed height */}
      <div className="relative w-3/4 max-w-4xl h-[500px]">
        {/* Form Section on Left */}
        <div className="absolute top-0 left-0 w-1/2 h-full p-10">
          <h2 className="text-3xl font-bold text-gray-800 text-center">Login</h2>
          <form className="mt-6" onSubmit={handleSubmit(onSubmit)}>
            <div className="mb-4">
              <label className="block text-sm font-medium text-gray-700">
                Your Email
              </label>
              <input
                type="email"
                {...register("email", {
                  required: "Email is required",
                  pattern: {
                    value:
                      /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
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
              <label className="block text-sm font-medium text-gray-700">
                Password
              </label>
              <input
                type="password"
                {...register("password", {
                  required: "Password is required",
                  pattern: {
                    value:
                      /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/,
                    message:
                      "Must be at least 6 characters and contain a number",
                  },
                })}
                className="w-full p-3 border rounded-lg mt-1 focus:ring-2 focus:ring-green-500"
              />
              {errors.password && (
                <p className="text-red-500 text-sm">
                  {errors.password.message}
                </p>
              )}
            </div>
            <button
              type="submit"
              className="w-full bg-green-600 text-white py-3 rounded-lg font-semibold hover:bg-green-700 transition-all"
            >
              Login
            </button>
          </form>
          <p className="mt-6 text-sm text-center">
            Don't have an account?{" "}
            <a
              href="/"
              className="text-green-600 font-semibold hover:underline"
            >
              Sign Up Here
            </a>
          </p>
        </div>

        {/* Overlay Section on Right */}
        <div className="absolute top-0 right-0 w-1/2 h-full p-10 bg-gradient-to-r from-black to-gray-800 text-white flex flex-col justify-center rounded-2xl">
          <div className="bg-white bg-opacity-30 backdrop-blur-lg p-10 rounded-2xl">
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
