import React from "react";

export default function Dashboard() {
  return (
    <div className="flex h-screen bg-gray-100">
      {/* Sidebar */}
      <aside className="w-64 bg-white p-4 shadow-lg">
        <h1 className="text-xl font-bold mb-6">EasyTrip Dashboard</h1>
        <nav>
          <ul className="space-y-2">
            <li>
              <a href="#" className="block text-gray-700 hover:text-blue-500">
                Dashboard
              </a>
            </li>
            <li>
              <a href="#" className="block text-gray-700 hover:text-blue-500">
                My Listings
              </a>
            </li>
            <li>
              <a href="#" className="block text-gray-700 hover:text-blue-500">
                Bookings
              </a>
            </li>
          </ul>
        </nav>
      </aside>

      {/* Main Content */}
      <main className="flex-1 p-6">
        <h2 className="text-2xl font-semibold mb-4">Welcome Back!</h2>

        {/* Guesthouse Listings */}
        <section className="mb-8">
          <h3 className="text-xl font-semibold mb-2">Your Guesthouse Listings</h3>
          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4">
            {/* Guesthouse 1 */}
            <div className="bg-white p-4 rounded-lg shadow">
              <div className="w-full h-40 bg-gray-200 flex items-center justify-center mb-3">
                300 x 200
              </div>
              <p className="text-green-600 font-bold">Approved</p>
            </div>
            {/* Guesthouse 2 */}
            <div className="bg-white p-4 rounded-lg shadow">
              <div className="w-full h-40 bg-gray-200 flex items-center justify-center mb-3">
                300 x 200
              </div>
              <p className="text-yellow-600 font-bold">Pending Approval</p>
            </div>
            {/* Guesthouse 3 */}
            <div className="bg-white p-4 rounded-lg shadow">
              <div className="w-full h-40 bg-gray-200 flex items-center justify-center mb-3">
                300 x 200
              </div>
              <p className="text-red-600 font-bold mb-2">Rejected</p>
              <button className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
                Edit &amp; Resubmit
              </button>
            </div>
          </div>
        </section>

        {/* Booking Management */}
        <section className="mb-8">
          <h3 className="text-xl font-semibold mb-2">Booking Management</h3>
          {/* New Booking Requests */}
          <div className="mb-4">
            <h4 className="font-semibold">New Booking Requests</h4>
            <div className="flex items-center space-x-4 mt-2">
              <span>John Doe</span>
              <button className="bg-green-500 text-white px-4 py-1 rounded hover:bg-green-600">
                Accept
              </button>
              <button className="bg-red-500 text-white px-4 py-1 rounded hover:bg-red-600">
                Decline
              </button>
            </div>
          </div>
          {/* Confirmed Bookings */}
          <div className="mb-4">
            <h4 className="font-semibold">Confirmed Bookings</h4>
            <p>Guest: Jane Smith | Check-in: 2023-10-15 | Check-out: 2023-10-20</p>
          </div>
          {/* Booking History */}
          <div>
            <h4 className="font-semibold">Booking History</h4>
            <p>Past reservations with earnings summary...</p>
          </div>
        </section>
      </main>
    </div>
  );
}
