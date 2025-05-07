import React from "react";
import { Link } from "react-router-dom";

const Sidebar = () => {
  return (
    <aside className="w-60 h-screen fixed top-0 left-0 bg-white shadow-md p-4 z-20">
      <h1 className="text-xl font-bold mb-6">EasyTrip Dashboard</h1>
      <ul className="space-y-4">
        <li>
          <Link to="/BUS_OWNER/dashboard" className="text-gray-700 hover:text-blue-600">
            Dashboard
          </Link>
        </li>
        <li>
          <Link to="/BUS_OWNER/listing" className="text-gray-700 hover:text-blue-600">
            My Listings
          </Link>
        </li>
        <li>
          <Link to="/BUS_OWNER/bookings" className="text-gray-700 hover:text-blue-600">
            Bookings
          </Link>
        </li>
      </ul>
    </aside>
  );
};

export default Sidebar;
