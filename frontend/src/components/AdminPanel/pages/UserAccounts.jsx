import React, { useState } from "react";
import { Link } from "react-router-dom";

const UserAccounts = () => {
  const [users, setUsers] = useState([
    { id: "001", name: "John Doe", email: "john.doe@example.com", date: "2023-01-15", role: "user" },
    { id: "002", name: "Jane Smith", email: "jane.smith@example.com", date: "2023-02-20", role: "Guesthouse Owner" },
    { id: "003", name: "Alice Johnson", email: "alice.johnson@example.com", date: "2023-03-10", role: "Guesthouse Owner" },
    { id: "004", name: "Bob Brown", email: "bob.brown@example.com", date: "2023-04-05", role: "Guesthouse Owner" },
    { id: "005", name: "Charlie Black", email: "charlie.black@example.com", date: "2023-05-15", role: "user" },
    { id: "006", name: "Diana Prince", email: "diana.prince@example.com", date: "2023-06-25", role: "user" },
    { id: "007", name: "Ethan Hunt", email: "ethan.hunt@example.com", date: "2023-07-30", role: "user" },
    { id: "008", name: "Fiona Glenanne", email: "fiona.glenanne@example.com", date: "2023-08-12", role: "user" },
    { id: "009", name: "George Costanza", email: "george.costanza@example.com", date: "2023-09-05", role: "user" },
    { id: "010", name: "Hannah Baker", email: "hannah.baker@example.com", date: "2023-10-01", role: "user" }
  ]);

  const [search, setSearch] = useState("");
  const [sortByDate, setSortByDate] = useState(false);

  const handleDelete = (id) => {
    setUsers(users.filter(user => user.id !== id));
  };

  const handleSort = () => {
    setSortByDate(!sortByDate);
    setUsers([...users].sort((a, b) => (sortByDate ? a.date.localeCompare(b.date) : b.date.localeCompare(a.date))));
  };

  return (
    <div className="p-6 bg-gray-900 min-h-screen text-white">
      <h2 className="text-2xl font-bold mb-4">User Accounts (Total: {users.length})</h2>
      
      <div className="flex gap-2 mb-4">
        <input
          type="text"
          placeholder="Search by User ID, Name, Email"
          className="p-2 rounded bg-gray-800 text-white w-1/2"
          onChange={(e) => setSearch(e.target.value.toLowerCase())}
        />
        <button onClick={handleSort} className="p-2 bg-blue-600 rounded">Sort by Date Created</button>
      </div>

      <div className="overflow-x-auto">
        <table className="w-full text-left border border-gray-700">
          <thead className="bg-gray-800">
            <tr>
              <th className="p-2">User ID</th>
              <th className="p-2">Full Name</th>
              <th className="p-2">Email Address</th>
              <th className="p-2">Registration Date</th>
              <th className="p-2">Role</th>
              <th className="p-2">Actions</th>
            </tr>
          </thead>
          <tbody>
            {users.filter(user => user.name.toLowerCase().includes(search) || user.email.toLowerCase().includes(search) || user.id.includes(search)).map((user) => (
              <tr key={user.id} className="border-b border-gray-700">
                <td className="p-2">{user.id}</td>
                <td className="p-2">{user.name}</td>
                <td className="p-2">{user.email}</td>
                <td className="p-2">{user.date}</td>
                <td className="p-2">{user.role}</td>
                <td className="p-2 flex gap-2">
                    <Link to={`/admin/userAccounts/${user.role}`} className="hover:text-blue-500 hover:underline p-1 px-3 bg-blue-600 rounded text-white">
                        View Details
                    </Link>
                    <button onClick={() => handleDelete(user.id)} className="p-1 px-3 bg-red-600 rounded">Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default UserAccounts;
