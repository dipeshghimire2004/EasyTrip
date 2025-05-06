import React from "react";

const BusDashboard = () => {
  return (
    <div className="flex h-screen bg-gray-100">
      {/* Sidebar */}
      <aside className="w-60 bg-white shadow-md p-4">
        <h1 className="text-xl font-bold mb-6">EasyTrip Dashboard</h1>
        <ul className="space-y-4">
          <li className="text-gray-700 hover:text-blue-600 cursor-pointer">Dashboard</li>
          <li className="text-gray-700 hover:text-blue-600 cursor-pointer">My Listings</li>
          <li className="text-gray-700 hover:text-blue-600 cursor-pointer">Bookings</li>
        </ul>
      </aside>

      {/* Main Content */}
      <main className="flex-1 p-6">
        <h2 className="text-2xl font-bold mb-4">Welcome Back!</h2>

        {/* Bus Listings */}
        <section className="mb-8">
          <h3 className="text-xl font-semibold mb-2">Your Bus Listings</h3>
          <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
            {/* Bus 1 */}
            <div className="bg-white rounded shadow p-4">
              <div className="w-full h-40 bg-gray-300 flex items-center justify-center mb-2">300 x 200</div>
              <p className="font-medium">Bus 1</p>
              <p className="text-green-600 text-sm font-semibold">✔️ Approved</p>
            </div>

            {/* Bus 2 */}
            <div className="bg-white rounded shadow p-4">
              <div className="w-full h-40 bg-gray-300 flex items-center justify-center mb-2">300 x 200</div>
              <p className="font-medium">Bus 2</p>
              <p className="text-yellow-600 text-sm font-semibold">⏳ Pending Approval</p>
            </div>

            {/* Bus 3 */}
            <div className="bg-white rounded shadow p-4">
              <div className="w-full h-40 bg-gray-300 flex items-center justify-center mb-2">300 x 200</div>
              <p className="font-medium">Bus 3</p>
              <p className="text-red-600 text-sm font-semibold">❌ Rejected</p>
              <p className="text-xs text-gray-500">Reason: Not enough amenities.</p>
              <button className="mt-2 px-4 py-1 text-white bg-blue-600 rounded hover:bg-blue-700 text-sm">
                Edit & Resubmit
              </button>
            </div>
          </div>
        </section>

        {/* Booking Management */}
        <section className="mb-8">
          <h3 className="text-xl font-semibold mb-2">Booking Management</h3>

          <div className="bg-white rounded shadow p-4 mb-4">
            <p className="font-semibold">New Booking Requests</p>
            <p>Booking from John Doe</p>
            <div className="mt-2 space-x-2">
              <button className="px-4 py-1 text-white bg-green-500 rounded hover:bg-green-600 text-sm">Accept</button>
              <button className="px-4 py-1 text-white bg-red-500 rounded hover:bg-red-600 text-sm">Decline</button>
            </div>
          </div>

          <div className="bg-white rounded shadow p-4 mb-4">
            <p className="font-semibold">Confirmed Bookings</p>
            <p>Guest: Jane Smith</p>
            <p>Booking: 2023-10-15 kathmandu to pokhara</p>
          </div>

          <div className="bg-white rounded shadow p-4">
            <p className="font-semibold">Booking History</p>
            <p>Past reservations with earnings summary…</p>
          </div>
        </section>

        {/* Notifications & Support */}
        <section>
          <h3 className="text-xl font-semibold mb-2">Notifications & Support</h3>
          <div className="bg-white rounded shadow p-4">
            <p className="font-semibold">Alerts</p>
            <p>New booking request received!</p>
            <p>Admin has updated your listing status.</p>
          </div>
        </section>
      </main>
    </div>
  );
};

export default BusDashboard;
