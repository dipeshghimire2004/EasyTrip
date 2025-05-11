import React, { useState } from "react";
import { Link } from "react-router-dom";

const Navbar = () => {
  const [menuOpen, setMenuOpen] = useState(false);

  return (
    <nav className="p-4 shadow-md bg-white relative">
      <div className="flex justify-between items-center">
        <Link to="/" className="text-xl font-bold hover:text-gray-600">EasyTrip</Link>

        {/* Hamburger Icon for Mobile */}
        <button
          className="md:hidden text-gray-600 focus:outline-none"
          onClick={() => setMenuOpen(!menuOpen)}
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
        <div className="hidden md:flex md:items-center md:space-x-4">
          <Link to="/" className="hover:text-gray-600">Home</Link>
          <Link to="/about" className="hover:text-gray-600">About Us</Link>
          <Link to="/contact" className="hover:text-gray-600">Contact</Link>
        </div>

        {/* Desktop Buttons */}
        <div className="hidden md:flex md:space-x-2">
          <Link to="/CLIENT/login">
            <button className="bg-blue-500 text-white px-4 py-2 rounded">Traveler Login/Register</button>
          </Link>
          <Link to="/HOTEL_MANAGER/login">
            <button className="bg-green-500 text-white px-4 py-2 rounded">Guesthouse Owner Login/Register</button>
          </Link>
          <Link to="/BUS_OPERATOR/signup">
            <button className="bg-yellow-500 text-white px-4 py-2 rounded">Bus Owner Login/Register</button>
          </Link>
        </div>
      </div>

      {/* Mobile Menu */}
      {menuOpen && (
        <div className="flex flex-col md:hidden mt-4 space-y-2">
          <Link to="/" className="hover:text-gray-600" onClick={() => setMenuOpen(false)}>Home</Link>
          <Link to="/about" className="hover:text-gray-600" onClick={() => setMenuOpen(false)}>About Us</Link>
          <Link to="/contact" className="hover:text-gray-600" onClick={() => setMenuOpen(false)}>Contact</Link>
          <Link to="/CLIENT/login">
            <button className="bg-blue-500 text-white px-4 py-2 rounded w-full">Traveler Login/Register</button>
          </Link>
          <Link to="/HOTEL_MANAGER/login">
            <button className="bg-green-500 text-white px-4 py-2 rounded w-full">Guesthouse Owner Login/Register</button>
          </Link>
          <Link to="/BUS_OPERATOR/signup">
            <button className="bg-yellow-500 text-white px-4 py-2 rounded w-full">Bus Owner Login/Register</button>
          </Link>
        </div>
      )}
    </nav>
  );
};

export default Navbar;
