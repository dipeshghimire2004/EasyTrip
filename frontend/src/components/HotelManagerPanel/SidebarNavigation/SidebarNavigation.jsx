// SidebarNavigation.js
import React from "react";
import { Link } from "react-router-dom";

export default function SidebarNavigation() {
  return (
    <aside className="w-64 bg-white p-4 shadow-lg">
      <h1 className="text-xl font-bold mb-6">EasyTrip Dashboard</h1>
      <nav>
        <ul className="space-y-2">
          <li>
            <Link to="/HOTEL_MANAGER/dashboard">
              <h2 className="block text-gray-700 hover:text-blue-500">
                Add Guesthouse
              </h2>
            </Link>
          </li>
          <li>
            <Link to="/HOTEL_MANAGER/addGuestHouse">
              <h2 className="block text-gray-700 hover:text-blue-500">
                Add Guesthouse
              </h2>
            </Link>
          </li>
          <li>
            <Link to="/HOTEL_MANAGER/guestHouseOverview">
              <h2 className="block text-gray-700 hover:text-blue-500">
                Guesthouse Overview
              </h2>
            </Link>
          </li>
        </ul>
      </nav>
    </aside>
  );
}
