import React, { useState } from 'react';
import Sidebar from '../Sidebar/Sidebar'; 
import BusCreate from '../Components/CreateBus/CreateBus';
import UpdateBus from '../Components/UpdateBus/UpdateBus';
import AddSchedule from '../Components/AddSchedule/AddSchedule';
import SearchSchedule from '../Components/SearchSchedule/SearchSchedule';
import UpdateSchedule from '../Components/UpdateSchedule/UpdateSchedule';

function ManageBuses() {
  const [active, setActive] = useState('create');

  const renderComponent = () => {
    switch (active) {
      case 'create': return <BusCreate />;
      case 'updateBus': return <UpdateBus />;
      case 'addSchedule': return <AddSchedule />;
      case 'searchSchedule': return <SearchSchedule />;
      case 'updateSchedule': return <UpdateSchedule scheduleId="123" />;
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
          <button onClick={() => setActive('updateBus')} className="px-4 py-2 bg-indigo-500 text-white rounded hover:bg-indigo-600">Update Bus</button>
          <button onClick={() => setActive('addSchedule')} className="px-4 py-2 bg-indigo-500 text-white rounded hover:bg-indigo-600">Add Schedule</button>
          <button onClick={() => setActive('searchSchedule')} className="px-4 py-2 bg-indigo-500 text-white rounded hover:bg-indigo-600">Search Schedule</button>
          <button onClick={() => setActive('updateSchedule')} className="px-4 py-2 bg-indigo-500 text-white rounded hover:bg-indigo-600">Update Schedule</button>
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
