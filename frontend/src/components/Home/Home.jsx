import React from "react";
import { Link } from "react-router-dom";
import Image from "../../assets/nepal.jpg"
import Layout from "../Layout/Layout";

const HeroSection = () => {
  return (
      <div className="flex flex-col items-center justify-center text-white h-[30rem] text-center bg-no-repeat bg-cover bg-center" style={{backgroundImage: `url(${Image})`}}>
        <h1 className="text-2xl font-bold">Discover Your Next Adventure</h1>
        <div className="flex space-x-2 p-2 rounded mt-4">
          <input type="text" placeholder="Where are you going?" className="p-2 border rounded bg-white text-gray-500" />
          <input type="text" placeholder="Check-in" className="p-2 border rounded bg-white text-gray-500" />
          <input type="text" placeholder="Check-out" className="p-2 border rounded bg-white text-gray-500" />
          <input type="text" placeholder="Guests" className="p-2 border rounded bg-white text-gray-500" />
        </div>
        <div className="space-x-2 mt-4">
          <Link to="/booking">
              <button className="bg-blue-500 text-white px-4 py-2 rounded">Find Guesthouses</button>
          </Link>
          <Link to="/HOTEL_MANAGER/addGuestHouse">
            <button className="bg-green-500 text-white px-4 py-2 rounded">List Your Guesthouse</button>
          </Link>
        </div>
      </div>
  );
};

const Features = () => {
  const features = [
    { title: "For Travelers", description: "Search for Guesthouses in your destination." },
    { title: "For Owners", description: "List your guesthouse easily with images and descriptions." },
    { title: "Booking", description: "Book securely and pay online." },
    { title: "Confirmation", description: "Get booking confirmations and check availability in real-time." },
  ];

  return (
    <div className="p-8">
      <h2 className="p-4 mb-4 text-2xl font-bold text-center">How It Works</h2>
      {features.map((feature, index) => (
        <div key={index} className="bg-gray-100 p-4 mb-4 rounded shadow">
          <h2 className="font-bold">{feature.title}:</h2>
          <p>{feature.description}</p>
        </div>
      ))}
    </div>
  );
};

const Home = () => {
  return (
    <Layout>
      <HeroSection />
      <Features />
    </Layout>
  );
};

export default Home;
