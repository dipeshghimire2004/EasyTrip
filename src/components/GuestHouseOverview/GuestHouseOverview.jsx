function GuestHouseOverview() {
  return (
    <div className="container mx-auto p-4 space-y-6 max-w-4xl">
      {/* Guesthouse Overview Card */}
      <div className="bg-white rounded-lg shadow-md overflow-hidden">
        <div className="p-6">
          <h2 className="text-xl font-semibold mb-4">Guesthouse Overview</h2>

          <div className="bg-gray-100 rounded-md overflow-hidden mb-4">
            <div className="relative w-full h-[250px] sm:h-[300px] md:h-[400px]">
              <img src="https://via.placeholder.com/600x400" alt="Guesthouse" className="w-full h-full object-cover" />
            </div>
            <div className="p-1">
              <button className="text-blue-600 text-sm hover:underline">Update Image</button>
            </div>
          </div>

          <div className="space-y-2">
            <div className="grid grid-cols-[140px_1fr] gap-1">
              <span className="font-semibold">Name:</span>
              <span>Cozy Cottage</span>
            </div>
            <div className="grid grid-cols-[140px_1fr] gap-1">
              <span className="font-semibold">Location:</span>
              <span>123 Main St, Springfield</span>
            </div>
            <div className="grid grid-cols-[140px_1fr] gap-1">
              <span className="font-semibold">Total Rooms:</span>
              <span>10</span>
            </div>
            <div className="grid grid-cols-[140px_1fr] gap-1 items-center">
              <span className="font-semibold">Availability Status:</span>
              <div>
                <span className="inline-flex px-3 py-1 text-green-600 bg-green-50 rounded-md w-auto">Available</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Detailed Information Card */}
      <div className="bg-white rounded-lg shadow-md overflow-hidden">
        <div className="p-6">
          <h2 className="text-xl font-semibold mb-4">Detailed Information</h2>

          <div className="space-y-4">
            <div>
              <h3 className="font-medium mb-2">Description:</h3>
              <textarea
                placeholder="Add description here..."
                className="min-h-[100px] w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-gray-200"
              />
            </div>

            <div>
              <h3 className="font-medium mb-2">Amenities:</h3>
              <textarea
                placeholder="Add amenities, comma-separated"
                className="min-h-[80px] w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-gray-200"
              />
            </div>
          </div>
        </div>
      </div>

      {/* Room Types & Prices Card */}
      <div className="bg-white rounded-lg shadow-md overflow-hidden">
        <div className="p-6">
          <h2 className="text-xl font-semibold mb-4">Room Types & Prices:</h2>

          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-50">
                <tr>
                  <th
                    scope="col"
                    className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                  >
                    Room Type
                  </th>
                  <th
                    scope="col"
                    className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                  >
                    Price
                  </th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                <tr>
                  <td className="px-6 py-4 whitespace-nowrap">Single Room</td>
                  <td className="px-6 py-4 whitespace-nowrap">$100</td>
                </tr>
                <tr>
                  <td className="px-6 py-4 whitespace-nowrap">Double Room</td>
                  <td className="px-6 py-4 whitespace-nowrap">$150</td>
                </tr>
                <tr>
                  <td className="px-6 py-4 whitespace-nowrap">Suite</td>
                  <td className="px-6 py-4 whitespace-nowrap">$250</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>

      {/* Booking Requests Card */}
      <div className="bg-white rounded-lg shadow-md overflow-hidden">
        <div className="p-6">
          <h2 className="text-xl font-semibold mb-4">Booking Requests:</h2>

          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-50">
                <tr>
                  <th
                    scope="col"
                    className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                  >
                    Guest Name
                  </th>
                  <th
                    scope="col"
                    className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                  >
                    Check-in
                  </th>
                  <th
                    scope="col"
                    className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                  >
                    Check-out
                  </th>
                  <th
                    scope="col"
                    className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                  >
                    Status
                  </th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                <tr>
                  <td className="px-6 py-4 whitespace-nowrap">John Doe</td>
                  <td className="px-6 py-4 whitespace-nowrap">2023-10-01</td>
                  <td className="px-6 py-4 whitespace-nowrap">2023-10-05</td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <span className="inline-block px-2 py-1 text-xs font-medium rounded-full bg-green-50 text-green-600">
                      Confirmed
                    </span>
                  </td>
                </tr>
                <tr>
                  <td className="px-6 py-4 whitespace-nowrap">Jane Smith</td>
                  <td className="px-6 py-4 whitespace-nowrap">2023-10-10</td>
                  <td className="px-6 py-4 whitespace-nowrap">2023-10-15</td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <span className="inline-block px-2 py-1 text-xs font-medium rounded-full bg-yellow-50 text-yellow-600">
                      Pending
                    </span>
                  </td>
                </tr>
                <tr>
                  <td className="px-6 py-4 whitespace-nowrap">Mike Johnson</td>
                  <td className="px-6 py-4 whitespace-nowrap">2023-10-20</td>
                  <td className="px-6 py-4 whitespace-nowrap">2023-10-25</td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <span className="inline-block px-2 py-1 text-xs font-medium rounded-full bg-red-50 text-red-600">
                      Cancelled
                    </span>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  )
}

export default GuestHouseOverview

 