import React from "react";

const bookings = [
  { id: 1, type: "Bus", destination: "Kathmandu", amount: "NPR 1200", status: "Completed" },
  { id: 2, type: "Room", destination: "Pokhara", amount: "NPR 2500", status: "Pending" },
  { id: 3, type: "Bus", destination: "Chitwan", amount: "NPR 800", status: "Completed" },
];

const statusClasses = {
  Completed: "bg-green-100 text-green-700",
  Pending: "bg-yellow-100 text-yellow-700",
};

export default function MyBookings() {
  return (
    <div className="p-6 bg-gray-50 min-h-screen">
      <div className="flex justify-between items-center mb-4">
        <h1 className="text-2xl font-semibold">My Bookings</h1>
        <span className="text-sm bg-gray-200 text-gray-700 px-2 py-1 rounded-full">3 Total</span>
        <div className="ml-auto flex gap-4 items-center">
          <input
            type="text"
            placeholder="Search bookings..."
            className="border border-gray-300 px-3 py-1 rounded-md text-sm"
          />
          <button className="bg-blue-600 text-white px-4 py-2 rounded-md text-sm">New Booking</button>
        </div>
      </div>

      <div className="mb-4 border-b">
        <ul className="flex gap-6 text-sm font-medium text-gray-700">
          <li className="border-b-2 border-black pb-2">All Bookings</li>
          <li>Bus Bookings</li>
          <li>Room Bookings</li>
        </ul>
      </div>

      <div className="bg-white rounded-lg shadow overflow-x-auto">
        <table className="w-full text-sm text-left">
          <thead className="bg-gray-100">
            <tr>
              <th className="p-4">Booking ID</th>
              <th className="p-4">Type</th>
              <th className="p-4">Destination</th>
              <th className="p-4">Amount</th>
              <th className="p-4">Status</th>
            </tr>
          </thead>
          <tbody>
            {bookings.map((booking) => (
              <tr key={booking.id} className="border-t">
                <td className="p-4">#{booking.id}</td>
                <td className="p-4">{booking.type}</td>
                <td className="p-4">{booking.destination}</td>
                <td className="p-4">{booking.amount}</td>
                <td className="p-4">
                  <span className={`px-2 py-1 rounded-full text-xs font-semibold ${statusClasses[booking.status]}`}>
                    {booking.status}
                  </span>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
