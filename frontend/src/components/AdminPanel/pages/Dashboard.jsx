import React from "react";
import { Link } from "react-router-dom";
import ActionButton from "../components/ActionButton";

const Dashboard = () => {
  return (
    <div className="p-8">
      {/* Dashboard Overview */}
      <h2 className="text-2xl font-bold mb-4">Dashboard Overview</h2>
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
        <div className="bg-white p-6 rounded shadow">
          <h3 className="text-lg font-medium">Guesthouse Listings</h3>
          <p className="text-gray-600 mt-2">Manage guesthouse listings.</p>
        </div>
        <div className="bg-white p-6 rounded shadow">
          <Link to="/admin/userAccounts" className="hover:text-blue-500 hover:underline">
            <h3 className="text-lg font-medium">User Accounts</h3>
            <p className="text-gray-600 mt-2">Manage user accounts and roles.</p>
          </Link>
        </div>
        <div className="bg-white p-6 rounded shadow">
          <h3 className="text-lg font-medium">Bookings</h3>
          <p className="text-gray-600 mt-2">Overview of recent bookings.</p>
        </div>
      </div>

      {/* Approval Requests Summary */}
      <div className="mb-8">
        <div className="flex items-center justify-between mb-4">
          <h2 className="text-2xl font-bold">Approval Requests Summary</h2>
          <Link to="/admin/approvallog" className="text-blue-500 hover:underline">
            View All
          </Link>
        </div>
        <table className="min-w-full bg-white shadow rounded">
          <thead>
            <tr className="bg-gray-200 text-gray-700">
              <th className="w-1/3 p-3 text-left">Guesthouse Name</th>
              <th className="w-1/3 p-3 text-left">Status</th>
              <th className="w-1/3 p-3 text-left">Actions</th>
            </tr>
          </thead>
          <tbody>
            {["Cozy Cottage", "Beachfront Villa"].map((house, index) => (
              <tr key={index} className="border-t">
                <td className="p-3">{house}</td>
                <td className="p-3">Pending</td>
                <td className="p-3">
                  <ActionButton
                    label="Accept"
                    type="accept"
                    onClick={() => alert(`Accepted ${house}`)}
                  />
                  <ActionButton
                    label="Reject"
                    type="reject"
                    onClick={() => alert(`Rejected ${house}`)}
                  />
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* Booking Logs Summary */}
      <div>
        <div className="flex items-center justify-between mb-4">
          <h2 className="text-2xl font-bold">Booking Logs Summary</h2>
          <Link to="/admin/bookinglog" className="text-blue-500 hover:underline">
            View All
          </Link>
        </div>
        <table className="min-w-full bg-white shadow rounded">
          <thead>
            <tr className="bg-gray-200 text-gray-700">
              <th className="p-3 text-left">Booking ID</th>
              <th className="p-3 text-left">Guest Name</th>
              <th className="p-3 text-left">Check-in</th>
              <th className="p-3 text-left">Check-out</th>
              <th className="p-3 text-left">Status</th>
              <th className="p-3 text-left">Guesthouse</th>
            </tr>
          </thead>
          <tbody>
            {[
              {
                id: "001",
                name: "John Doe",
                checkIn: "2023-09-15",
                checkOut: "2023-09-20",
                status: "Pending",
                guesthouse: "ABC",
              },
              {
                id: "002",
                name: "Jane Smith",
                checkIn: "2023-09-22",
                checkOut: "2023-09-25",
                status: "Confirmed",
                guesthouse: "Cozy Guesthouse",
              },
            ].map((booking) => (
              <tr key={booking.id} className="border-t">
                <td className="p-3">{booking.id}</td>
                <td className="p-3">{booking.name}</td>
                <td className="p-3">{booking.checkIn}</td>
                <td className="p-3">{booking.checkOut}</td>
                <td className="p-3">{booking.status}</td>
                <td className="p-3">{booking.guesthouse}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Dashboard;
