import React from "react";

const BookingLog = () => {
  const bookings = [
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
    {
      id: "003",
      name: "Alice Johnson",
      checkIn: "2023-09-29",
      checkOut: "2023-10-02",
      status: "Pending",
      guesthouse: "Cozy Cottage",
    },
    {
      id: "004",
      name: "Bob Brown",
      checkIn: "2023-10-05",
      checkOut: "2023-10-10",
      status: "Cancelled",
      guesthouse: "Urban Loft",
    },
  ];

  return (
    <div className="p-8">
      <h2 className="text-2xl font-bold mb-4">Booking Logs</h2>
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
          {bookings.map((booking) => (
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
  );
};

export default BookingLog;
