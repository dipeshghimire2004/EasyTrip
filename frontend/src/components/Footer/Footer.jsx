import React from "react";
import { Link } from "react-router-dom";

const Footer = () => {
  return (
    <footer className="bg-gray-100 py-6 text-gray-700 text-sm">
      <div className="container mx-auto px-6 grid grid-cols-1 md:grid-cols-4 gap-6">
        <div>
          <h2 className="font-semibold text-gray-900">About EasyTrip</h2>
          <p className="mt-2 text-gray-600">
            EasyTrip simplifies travel bookings, connecting travelers with guesthouses effortlessly.
          </p>
          <Link to="/about" className="text-blue-600 hover:underline block mt-2">About Us</Link>
        </div>

        <div>
          <h2 className="font-semibold text-gray-900">Quick Links</h2>
          <ul className="mt-2 space-y-1">
            <li><Link to="/" className="hover:underline">Home</Link></li>
            <li><Link to="/booking" className="hover:underline">Search Guesthouses</Link></li>
            <li><Link to="#" className="hover:underline">My Bookings</Link></li>
            <li><Link to="#" className="hover:underline">Contact Us</Link></li>
          </ul>
        </div>

        <div>
          <h2 className="font-semibold text-gray-900">Support & Policies</h2>
          <ul className="mt-2 space-y-1">
            <li><Link to="#" className="hover:underline">Privacy Policy</Link></li>
            <li><Link to="#" className="hover:underline">Terms & Conditions</Link></li>
            <li><Link to="#" className="hover:underline">Customer Support</Link></li>
          </ul>
        </div>

        <div>
          <h2 className="font-semibold text-gray-900">Social Media & Contact</h2>
          <div className="flex space-x-4 mt-2">
            <a href="#" className="hover:text-blue-600"><i className="fab fa-facebook-f"></i></a>
            <a href="#" className="hover:text-blue-600"><i className="fab fa-instagram"></i></a>
            <a href="#" className="hover:text-blue-600"><i className="fab fa-twitter"></i></a>
            <a href="#" className="hover:text-blue-600"><i className="fab fa-linkedin-in"></i></a>
          </div>
          <p className="mt-2">
            Email: <a href="mailto:support@easytrip.com" className="text-blue-600 hover:underline">support@easytrip.com</a>
          </p>
          <p>Phone: + XXXXXXX</p>
        </div>
      </div>
      <div className="border-t mt-6 pt-4 text-center text-gray-500">
        Copyright &copy; EasyTrip. All rights reserved.
      </div>
    </footer>
  );
};

export default Footer;
