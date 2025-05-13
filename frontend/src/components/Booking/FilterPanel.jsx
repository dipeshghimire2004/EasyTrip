// src/components/FilterPanel.jsx
import React from "react";

const FilterPanel = ({
  searchTerm,
  setSearchTerm,
  selectedLocation,
  setSelectedLocation,
  customLocation,
  setCustomLocation,
  selectedAmenities,
  setSelectedAmenities,
}) => {
  const amenitiesOptions = ["Wi-Fi", "Pool", "Gym", "Parking", "Breakfast"];

  const handleAmenityChange = (amenity) => {
    setSelectedAmenities((prev) =>
      prev.includes(amenity)
        ? prev.filter((a) => a !== amenity)
        : [...prev, amenity]
    );
  };

  return (
    <div className="mb-6 flex flex-wrap items-center gap-4">
      <input
        type="text"
        placeholder="Search hotels"
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        className="px-4 py-2 border rounded w-72"
      />

      <select
        value={selectedLocation}
        onChange={(e) => setSelectedLocation(e.target.value)}
        className="px-3 py-2 border rounded"
      >
        <option>All ( Location )</option>
        <option>Custom</option>
        <option>Kathmandu</option>
        <option>Pokhara</option>
      </select>

      {selectedLocation === "Custom" && (
        <input
          type="text"
          placeholder="Enter location"
          value={customLocation}
          onChange={(e) => setCustomLocation(e.target.value)}
          className="px-3 py-2 border rounded"
        />
      )}

      <div className="flex gap-2 flex-wrap">
        {amenitiesOptions.map((a) => (
          <label key={a} className="flex items-center gap-1 text-sm">
            <input
              type="checkbox"
              checked={selectedAmenities.includes(a)}
              onChange={() => handleAmenityChange(a)}
            />
            {a}
          </label>
        ))}
      </div>
    </div>
  );
};

export default FilterPanel;
