import React from "react";
import { Link } from "react-router-dom"; // ✅ Added import

const Navbar = () => {
    return (
      <nav className="flex justify-between items-center p-4 shadow-md">
        <div className="text-xl font-bold">EasyTrip</div>
        <div className="space-x-4">
          <Link to="/" className="hover:text-gray-600">Home</Link>
          <Link to="/about" className="hover:text-gray-600">About Us</Link>
          <Link to="#" className="hover:text-gray-600">Contact</Link>
        </div>
        <div className="space-x-2">
          <Link to="/CLIENT/login">
              <button className="bg-blue-500 text-white px-4 py-2 rounded">Traveler Login/Register</button>
          </Link>
          <Link to="/HOTEL_MANAGER/login">
              <button className="bg-green-500 text-white px-4 py-2 rounded">Guesthouse Owner Login/Register</button>
          </Link>
        </div>
      </nav>
    );
};

export default Navbar;
