import { useState } from "react" 
import api from "../../API/api"
import { Link } from 'react-router-dom';

export default function GuestHouseDashboard() {
  const [formData, setFormData] = useState({
    name: "",
    location: "",
    contactDetails: "",
    verifiedDocument: null,
    description: "",
    amenities: "",
    pricePerNight: "",
  })

  const handleChange = (e) => {
    const { name, value, type, files } = e.target
    setFormData((prev) => ({
      ...prev,
      [name]: type === "file" ? files[0] : value,
    }))
  }

  const handleSubmit = (e) => {
    e.preventDefault()

    const form = new FormData()
    form.append("name", formData.name)
    form.append("location", formData.location)
    form.append("contactDetails", formData.contactDetails)
    form.append("description", formData.description)
    form.append("amenities", formData.amenities)
    form.append("pricePerNight", formData.pricePerNight)
    form.append("status", "pending")

    if (formData.verifiedDocument) {
      form.append("verifiedDocument", formData.verifiedDocument)
    }

    const token = localStorage.getItem("accessToken")

    api.post("/api/guesthouses", form)
      .then((response) => {
        console.log("Guesthouse data submitted successfully:", response.data)
      })
      .catch((error) => {
        console.error("Error submitting data:", error.response?.data || error.message)
      })
  }

  return (
    <div className="w-full min-h-screen bg-white">
      {/* Header */}
      <header className="w-full flex justify-between items-center p-4 border-b shadow-sm">
        <h1 className="text-xl font-medium">Guesthouse Owner Dashboard</h1>
        <div className="flex gap-4">
          <Link to="/HOTEL_MANAGER/guestHouseOverview">
            <h2 className="text-blue-500 text-sm">back to Dashboard</h2>
          </Link>
        </div>
      </header>

      {/* Add Guesthouse Form */}
      <div className="w-full p-6">
        <h2 className="text-lg font-medium mb-4">Add Guesthouse</h2>

        <form className="w-full space-y-4" onSubmit={handleSubmit}>
          <div>
            <label className="block text-sm mb-1">Guesthouse Name</label>
            <input
              name="name"
              type="text"
              onChange={handleChange}
              className="w-full p-2 border rounded-md"
              placeholder="Cozy Haven"
            />
          </div>

          <div>
            <label className="block text-sm mb-1">Location</label>
            <input
              name="location"
              type="text"
              onChange={handleChange}
              className="w-full p-2 border rounded-md"
              placeholder="Kathmandu"
            />
          </div>

          <div>
            <label className="block text-sm mb-1">Contact Details</label>
            <input
              name="contactDetails"
              type="email"
              onChange={handleChange}
              className="w-full p-2 border rounded-md"
              placeholder="owner@example.com"
            />
          </div>

          <div>
            <label className="block text-sm mb-1">Verified Document</label>
            <input
              name="verifiedDocument"
              type="file"
              onChange={handleChange}
              className="w-full p-2 border rounded-md"
            />
          </div>

          <div>
            <label className="block text-sm mb-1">Description</label>
            <textarea
              name="description"
              onChange={handleChange}
              rows={4}
              className="w-full p-2 border rounded-md"
              placeholder="Describe your guesthouse"
            />
          </div>

          <div>
            <label className="block text-sm mb-1">Amenities</label>
            <input
              name="amenities"
              type="text"
              onChange={handleChange}
              className="w-full p-2 border rounded-md"
              placeholder="Wi-Fi, Pool, Gym"
            />
          </div>

          <div>
            <label className="block text-sm mb-1">Price Per Night ($)</label>
            <input
              name="pricePerNight"
              type="number"
              step="0.01"
              onChange={handleChange}
              className="w-full p-2 border rounded-md"
              placeholder="10"
            />
          </div>

          <div>
            <button
              type="submit"
              className="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600"
            >
              Add Guesthouse Room
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}
