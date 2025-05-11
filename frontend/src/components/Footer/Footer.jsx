import React from "react";
import { Link } from "react-router-dom";
import { FaTwitter, FaFacebookF, FaInstagram, FaLinkedinIn, FaCcVisa, FaCcMastercard, FaCcPaypal, FaGooglePay } from "react-icons/fa";

const Footer = () => {
  return (
      <>
        <div className="bg-black text-white py-6 px-6 flex justify-center items-center">
          <p className="text-xl font-bold text-center">
            "Travel far, explore deep—every journey shapes a story of adventure and discovery!"
          </p>
        </div>
        <footer className="bg-gray-100 py-6 text-gray-700 text-sm">
          <div className="container mx-auto px-6 grid grid-cols-1 md:grid-cols-5 gap-6">
            <div>
              <h2 className="font-bold text-gray-900 uppercase">EasyTrip</h2>
              <p className="mt-2 text-gray-600">
                EasyTrip simplifies travel bookings, connecting travelers with guesthouses effortlessly.
              </p>
              <div className="flex space-x-4 mt-2">
                <a href="#" className="hover:text-blue-600"><FaTwitter /></a>
                <a href="#" className="hover:text-blue-600"><FaFacebookF /></a>
                <a href="#" className="hover:text-blue-600"><FaInstagram /></a>
                <a href="#" className="hover:text-blue-600"><FaLinkedinIn /></a>
              </div>
            </div>

            <div>
              <h2 className="font-bold text-gray-900 uppercase">Company</h2>
              <ul className="mt-2 space-y-1">
                <li><Link to="/about" className="hover:underline">About Us</Link></li>
                <li><Link to="#" className="hover:underline">Features</Link></li>
                <li><Link to="#" className="hover:underline">Works</Link></li>
                <li><Link to="#" className="hover:underline">Career</Link></li>
              </ul>
            </div>

            <div>
              <h2 className="font-bold text-gray-900 uppercase">Help</h2>
              <ul className="mt-2 space-y-1">
                <li><Link to="#" className="hover:underline">Customer Support</Link></li>
                <li><Link to="#" className="hover:underline">Delivery Details</Link></li>
                <li><Link to="#" className="hover:underline">Terms & Conditions</Link></li>
                <li><Link to="#" className="hover:underline">Privacy Policy</Link></li>
              </ul>
            </div>

            <div>
              <h2 className="font-bold text-gray-900 uppercase">FAQ</h2>
              <ul className="mt-2 space-y-1">
                <li><Link to="#" className="hover:underline">Account</Link></li>
                <li><Link to="#" className="hover:underline">My Bookings</Link></li>
                <li><Link to="#" className="hover:underline">Orders</Link></li>
                <li><Link to="#" className="hover:underline">Payments</Link></li>
              </ul>
            </div>

            <div>
              <h2 className="font-bold text-gray-900 uppercase">Resources</h2>
              <ul className="mt-2 space-y-1">
                <li><Link to="/booking" className="hover:underline">Search Guesthouses</Link></li>
                <li><Link to="#" className="hover:underline">Travel Guides</Link></li>
                <li><Link to="#" className="hover:underline">Blog</Link></li>
                <li><Link to="#" className="hover:underline">Tutorials</Link></li>
              </ul>
            </div>
          </div>
          <div className="border-t mt-6 pt-4 text-center text-gray-500 flex justify-between items-center px-6">
            <p>EasyTrip © 2000-2025. All Rights Reserved</p>
            <div className="flex space-x-2">
              <FaCcVisa className="h-6 w-6" />
              <FaCcMastercard className="h-6 w-6" />
              <FaCcPaypal className="h-6 w-6" />
              <FaGooglePay className="h-6 w-6" />
            </div>
          </div>
        </footer>
      </>
  );
};

export default Footer;