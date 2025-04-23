import React from 'react';

const BusBookingPayment = () => {
  return (
    <div className="min-h-screen bg-gray-100">
      {/* Navbar */}
      <nav className="bg-white shadow px-6 py-4 flex items-center justify-between">
        <div className="flex items-center space-x-2">
          <div className="w-8 h-8 bg-gray-200 rounded" />
          <span className="font-semibold text-lg">EasyTrip</span>
        </div>
        <ul className="flex items-center space-x-6 text-gray-600">
          <li className="hover:text-blue-600 cursor-pointer">Home</li>
          <li className="hover:text-blue-600 cursor-pointer">Dashboard</li>
          <li className="flex items-center space-x-2 hover:text-blue-600 cursor-pointer">
            <div className="w-8 h-8 bg-gray-200 rounded-full" />
            <span>Admin</span>
          </li>
        </ul>
      </nav>

      {/* Main Content */}
      <div className="container mx-auto px-6 py-8">
        <div className="bg-white rounded-lg shadow p-6">
          <h2 className="text-2xl font-semibold">Complete Your Booking</h2>
          
          <div className="mt-6 flex flex-col md:flex-row gap-6">
            {/* Left Panel */}
            <div className="flex-1 bg-gray-50 rounded-lg p-6 space-y-4">
              <div className="flex items-center space-x-3">
                <div className="w-10 h-10 bg-blue-100 rounded flex items-center justify-center">
                  📄
                </div>
                <div>
                  <h3 className="font-medium text-lg">Booking Summary</h3>
                  <p className="text-gray-500 text-sm">Please review your booking details</p>
                </div>
              </div>

              <dl className="grid grid-cols-1 sm:grid-cols-2 gap-y-2 text-gray-700">
                <dt>Booking ID</dt><dd className="font-medium">BK-2024-1234</dd>
                <dt>Type</dt><dd className="font-medium">Bus Booking</dd>
                <dt>Route</dt><dd className="font-medium">KTM to PKR</dd>
                <dt>Date</dt><dd className="font-medium">April 20, 2025</dd>
                <dt>Seats</dt><dd className="font-medium">2</dd>
                <dt>Total Amount</dt><dd className="font-medium">NPR 1500</dd>
              </dl>

              <div className="mt-4 text-center">
                <p className="text-gray-500 text-sm">This payment link expires in</p>
                <p className="text-xl font-semibold">10:00</p>
              </div>

              <button className="w-full bg-blue-600 text-white py-3 rounded-lg hover:bg-blue-700 transition">
                Proceed to Payment
              </button>
            </div>

            {/* Right Panel */}
            <div className="flex-1 bg-gray-50 rounded-lg p-6 flex flex-col items-center justify-center">
              <img
                src="https://via.placeholder.com/300"
                alt="QR code"
                className="w-72 h-72 object-cover rounded"
              />
              <p className="mt-4 text-gray-600 text-center text-sm">
                Scan this QR code or click the payment button to simulate your payment
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default BusBookingPayment;
