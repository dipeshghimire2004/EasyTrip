export default function GuestHouseDashboard() {
    return (
      <div className="w-full min-h-screen bg-white">
        {/* Header */}
        <header className="w-full flex justify-between items-center p-4 border-b shadow-sm">
          <h1 className="text-xl font-medium">Guesthouse Owner Dashboard</h1>
          <div className="flex gap-4">
            <a href="#" className="text-blue-500 text-sm">
              Add Guesthouse
            </a>
            <a href="#" className="text-blue-500 text-sm">
              Booking Management
            </a>
          </div>
        </header>
  
        {/* Add Guesthouse Form */}
        <div className="w-full p-6">
          <h2 className="text-lg font-medium mb-4">Add Guesthouse</h2>
  
          <form className="w-full space-y-4">
            <div>
              <label className="block text-sm mb-1">Guesthouse Name</label>
              <input
                type="text"
                placeholder="Enter guesthouse name"
                className="w-full p-2 border rounded-md focus:outline-none focus:ring-1 focus:ring-blue-500"
              />
            </div>
  
            <div>
              <label className="block text-sm mb-1">Images</label>
              <input
                type="file"
                className="w-full p-2 border rounded-md focus:outline-none focus:ring-1 focus:ring-blue-500"
              />
            </div>
  
            <div>
              <label className="block text-sm mb-1">Description</label>
              <textarea
                placeholder="Describe your guesthouse"
                rows={4}
                className="w-full p-2 border rounded-md focus:outline-none focus:ring-1 focus:ring-blue-500"
              />
            </div>
  
            <div>
              <label className="block text-sm mb-1">Amenities</label>
              <input
                type="text"
                placeholder="List amenities (comma-separated)"
                className="w-full p-2 border rounded-md focus:outline-none focus:ring-1 focus:ring-blue-500"
              />
            </div>
  
            <div>
              <button
                type="submit"
                className="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50"
              >
                Add Guesthouse Room
              </button>
            </div>
          </form>
        </div>
      </div>
    )
  }