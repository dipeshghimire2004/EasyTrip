import React from "react";
import { NavLink } from "react-router-dom";

const Sidebar = () => {
  const navItems = [
    { name: "Dashboard", path: "/admin/panel" },
    { name: "Booking Log", path: "/admin/bookinglog" },
    { name: "Approval Log", path: "/admin/approvallog" },
    { name: "Bus Approval Log", path: "/admin/busApprovallog" },
  ];

  return (
    <div className="w-64 h-screen bg-gray-800 text-white p-6">
      <h1 className="text-2xl font-bold mb-8">Admin Panel</h1>
      <ul>
        {navItems.map((item) => (
          <li key={item.name} className="mb-2">
            <NavLink
              to={item.path}
              className={({ isActive }) =>
                `block p-3 rounded ${isActive ? "bg-gray-600" : "hover:bg-gray-700"}`
              }
            >
              {item.name}
            </NavLink>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Sidebar;
