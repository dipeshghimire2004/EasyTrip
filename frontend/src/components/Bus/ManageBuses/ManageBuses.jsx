import React, { useState } from 'react';
import Sidebar from '../Sidebar/Sidebar'; 
import BusCreate from '../Components/CreateBus/CreateBus';
import ViewClientBookings from '../Components/ViewClientBookings/ViewClientBookings'; // Assuming this is the new component

function ManageBuses() {
  const [active, setActive] = useState('create');

  const renderComponent = () => {
    switch (active) {
      case 'create': return <BusCreate />;
      case 'viewClientBookings': return <ViewClientBookings />;
      default: return null;
    }
  };

  return (
    <div className="flex min-h-screen bg-gray-50">
      {/* Sidebar */}
      <Sidebar />

      {/* Main Content */}
      <div className="flex-1 ml-60 p-6">
        {/* Centered Navigation buttons */}
        <nav className="flex flex-wrap justify-center gap-2 mb-6">
          <button onClick={() => setActive('create')} className="px-4 py-2 bg-indigo-500 text-white rounded hover:bg-indigo-600">Create Bus</button>
          <button onClick={() => setActive('viewClientBookings')} className="px-4 py-2 bg-indigo-500 text-white rounded hover:bg-indigo-600">View Client Bookings</button>
        </nav>

        {/* Render selected component */}
        <div className="max-w-2xl mx-auto">
          {renderComponent()}
        </div>
      </div>
    </div>
  );
}

export default ManageBuses;
