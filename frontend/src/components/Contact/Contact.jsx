import { FaFacebook, FaInstagram } from "react-icons/fa";
import Layout from "../Layout/Layout";

const Contact = () => {
  return (
      <Layout>
        <div className="flex items-center justify-center min-h-screen bg-gradient-to-b from-green-50 to-white px-4">
          <div className="bg-white shadow-2xl rounded-3xl p-10 border border-green-300 max-w-lg w-full transition-all duration-300">
            <h2 className="text-4xl font-extrabold text-green-700 text-center mb-6 tracking-wide">
              Contact Us
            </h2>

            <div className="space-y-5 text-left text-lg">
              <p className="text-gray-800">
                <span className="font-semibold">📧 Email:</span>{" "}
                <a
                    href="mailto:Easytrip@gmail.com"
                    className="text-green-600 hover:underline"
                >
                  Easytrip@gmail.com
                </a>
              </p>
              <p className="text-gray-800">
                <span className="font-semibold">📞 Phone:</span>{" "}
                <a
                    href="tel:+9779800000000"
                    className="text-green-600 hover:underline"
                >
                  +977-9800000000
                </a>
              </p>
              <p className="text-gray-800">
                <span className="font-semibold">📍 Address:</span> Kathmandu, Nepal
              </p>
            </div>

            <div className="mt-8 flex justify-center space-x-8">
              <a
                  href="#"
                  aria-label="Facebook"
                  className="text-green-700 hover:text-green-900 text-4xl transform hover:scale-110 transition duration-200"
              >
                <FaFacebook />
              </a>
              <a
                  href="#"
                  aria-label="Instagram"
                  className="text-pink-600 hover:text-pink-800 text-4xl transform hover:scale-110 transition duration-200"
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
