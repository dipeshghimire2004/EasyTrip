import React, { useState } from "react";
import { Link } from "react-router-dom";

const Navbar = () => {
  const [menuOpen, setMenuOpen] = useState(false);

  return (
      <nav className="p-4 shadow-lg bg-gradient-to-r from-blue-200 via-purple-200 to-pink-100 relative rounded-lg">
        <div className="flex justify-between items-center">
          {/* Logo */}
          <Link to="/" className="text-3xl font-extrabold text-black hover:text-gray-700 transition-all duration-300 ease-in-out">
            EasyTrip
          </Link>

          {/* Hamburger Icon for Mobile */}
          <button
              className="md:hidden text-black focus:outline-non6"
 hover:underline              onClick={() => setMenuOpen(!menuOpen)}
          >
            <svg
                className="w-6 h-6"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
            >
              {menuOpen ? (
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M6 18L18 6M6 6l12 12" />
              ) : (
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M4 6h16M4 12h16M4 18h16" />
              )}
            </svg>
          </button>

          {/* Desktop Menu */}
          <div className="hidden md:flex md:items-center md:space-x-8">
            <Link to="/" className="text-black hover:text-gray-600 hover:underline transition-all duration-300 ease-in-out">Home</Link>
            <Link to="/about" className="text-black hover:text-gray-600 hover:underline transition-all duration-300 ease-in-out">About Us</Link>
            <Link to="/contact" className="text-black hover:text-gray-600 hover:underline transition-all duration-300 ease-in-out">Contact</Link>
          </div>

          {/* Desktop Buttons */}
          <div className="hidden md:flex md:space-x-4">
            <Link to="/CLIENT/login">
              <button className="bg-transparent hover:bg-blue-500 border-1 border-gray-400 text-black hover:text-white font-bold px-5 py-2 rounded-full text-lg shadow-lg transition-all duration-300 ease-in-out">
                Traveler Login
              </button>
            </Link>
            <Link to="/HOTEL_MANAGER/login">
              <button className="bg-transparent hover:bg-yellow-500 border-1 border-gray-400 text-black hover:text-white font-bold px-5 py-2 rounded-full text-lg shadow-lg transition-all duration-300 ease-in-out">
                Guesthouse Login
              </button>
            </Link>
            <Link to="/BUS_OPERATOR/signup">
              <button className="bg-transparent hover:bg-green-500 border-1 border-gray-400 text-black hover:text-white font-bold px-5 py-2 rounded-full text-lg shadow-lg transition-all duration-300 ease-in-out">
                Bus Login
              </button>
            </Link>
          </div>
        </div>

        {/* Mobile Menu */}
        {menuOpen && (
            <div className="flex flex-col md:hidden mt-4 space-y-4 p-4 bg-white rounded-xl shadow-xl">
              <Link to="/" className="text-gray-800 hover:text-blue-600 transition-all duration-300 ease-in-out" onClick={() => setMenuOpen(false)}>
                Home
              </Link>
              <Link to="/about" className="text-gray-800 hover:text-blue-600 transition-all duration-300 ease-in-out" onClick={() => setMenuOpen(false)}>
                About Us
              </Link>
              <Link to="/contact" className="text-gray-800 hover:text-blue-600 transition-all duration-300 ease-in-out" onClick={() => setMenuOpen(false)}>
                Contact
              </Link>
              <Link to="/CLIENT/login">
                <button className="bg-blue-600 hover:bg-blue-700 text-white px-6 py-3 rounded-full text-lg w-full transition-all duration-300 ease-in-out">
                  Traveler Login/Register
                </button>
              </Link>
              <Link to="/HOTEL_MANAGER/login">
                <button className="bg-green-600 hover:bg-green-700 text-white px-6 py-3 rounded-full text-lg w-full transition-all duration-300 ease-in-out">
                  Guesthouse Owner Login/Register
                </button>
              </Link>
              <Link to="/BUS_OPERATOR/signup">
                <button className="bg-yellow-600 hover:bg-yellow-700 text-white px-6 py-3 rounded-full text-lg w-full transition-all duration-300 ease-in-out">
                  Bus Owner Login/Register
                </button>
              </Link>
            </div>
        )}
      </nav>
  );
};

export default Navbar;
