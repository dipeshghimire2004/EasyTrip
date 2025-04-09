import { FaFacebook, FaInstagram } from "react-icons/fa";
import Layout from "../Layout/Layout";

const Contact = () => {
  return (
    <Layout>
      <div className="flex items-center justify-center min-h-screen bg-green-100">
        <div className="bg-white shadow-lg rounded-2xl p-6 border-2 border-green-400 max-w-sm text-center">
          <h2 className="text-2xl font-bold text-green-600 mb-4">Contact Us</h2>
          <p className="text-gray-800 text-lg">
            <span className="font-bold">Email:</span> Easytrip@gmail.com
          </p>
          <p className="text-gray-800 text-lg">
            <span className="font-bold">Phone:</span> +977-9800000000
          </p>
          <p className="text-gray-800 text-lg">
            <span className="font-bold">Address:</span> Kathmandu, Nepal
          </p>
          <div className="flex justify-center mt-4 space-x-6">
            <a
              href="#"
              className="text-blue-600 text-3xl hover:text-blue-800 transition"
              aria-label="Facebook"
            >
              <FaFacebook />
            </a>
            <a
              href="#"
              className="text-pink-600 text-3xl hover:text-pink-800 transition"
              aria-label="Instagram"
            >
              <FaInstagram />
            </a>
          </div>
        </div>
      </div>
    </Layout>
  );
};

export default Contact;