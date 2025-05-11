import React from "react";
import { Link } from "react-router-dom";
import { motion } from "framer-motion";
import { FaMapMarkerAlt, FaCalendarAlt, FaUser } from "react-icons/fa";
import { FaHotel, FaUserTie, FaCalendarCheck, FaCheckCircle } from "react-icons/fa";
import Image from "../../assets/nepal.jpg";  // Ensure the image path is correct
import Layout from "../Layout/Layout";

// Animation variants
const fadeUp = {
    hidden: { opacity: 0, y: 30 },
    visible: (i = 0) => ({
        opacity: 1,
        y: 0,
        transition: { delay: i * 0.2, duration: 0.6 },
    }),
};
const HeroSection = () => {
    return (
        <div
            className="relative flex flex-col items-center justify-center text-white h-[36rem] text-center bg-no-repeat bg-cover bg-center"
            style={{ backgroundImage: `url(${Image})` }}
        >
            {/* Overlay */}
            <div className="absolute inset-0 bg-black/60" />

            {/* Headline */}
            <motion.h1
                className="relative z-10 text-4xl md:text-6xl font-extrabold leading-tight max-w-4xl px-4"
                initial="hidden"
                animate="visible"
                variants={fadeUp}
            >
                Discover Your <span className="text-yellow-400">Next Adventure</span>
            </motion.h1>

            {/* Subheading */}
            <motion.p
                className="relative z-10 mt-4 text-lg md:text-xl text-gray-200 max-w-2xl px-4"
                initial="hidden"
                animate="visible"
                variants={fadeUp}
                custom={1}
            >
                Explore breathtaking destinations, book cozy stays, and travel with ease.
            </motion.p>

            {/* Action Buttons */}
            <motion.div
                className="relative z-10 flex flex-col md:flex-row space-y-3 md:space-y-0 md:space-x-4 mt-8 w-full max-w-2xl px-4"
                initial="hidden"
                animate="visible"
                variants={fadeUp}
                custom={2}
            >
                <Link to="/booking" className="w-full">
                    <button className="bg-blue-500 text-white px-4 py-3 rounded w-full hover:bg-blue-600 hover:scale-105 transition-transform shadow-lg">
                        Find Guesthouses
                    </button>
                </Link>
                <Link to="/busSearch" className="w-full">
                    <button className="bg-yellow-500 text-white px-4 py-3 rounded w-full hover:bg-yellow-600 hover:scale-105 transition-transform shadow-lg">
                        Find Buses
                    </button>
                </Link>
                <Link to="/HOTEL_MANAGER/addGuestHouse" className="w-full">
                    <button className="bg-green-500 text-white px-4 py-3 rounded w-full hover:bg-green-600 hover:scale-105 transition-transform shadow-lg">
                        List Your Guesthouse
                    </button>
                </Link>
            </motion.div>
        </div>
    );
};



const Features = () => {
    return (
        <section className="bg-gradient-to-r from-blue-100 via-purple-100 to-pink-100 py-20">
            <div className="max-w-screen-xl mx-auto px-6 text-black text-center">
                <h2 className="text-4xl font-extrabold mb-8">
                    Why Choose EasyTrip?
                </h2>
                <p className="text-lg mb-12 opacity-80">
                    Discover the features that make your travel experience seamless and unforgettable.
                </p>

                {/* Features Grid */}
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-12">

                    {/* Feature 1 */}
                    <div className="bg-white text-gray-900 p-8 rounded-3xl shadow-lg transform transition duration-500 hover:scale-105 hover:shadow-xl hover:bg-blue-50">
                        <div className="mb-6">
                            <svg className="w-16 h-16 text-blue-500 mx-auto" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 3v18M3 12h18" />
                            </svg>
                        </div>
                        <h3 className="text-2xl font-semibold mb-4">Easy Booking</h3>
                        <p className="text-gray-700">
                            Book your trips in just a few clicks, whether it's a flight, hotel, or bus ride. We make booking fast and effortless.
                        </p>
                    </div>

                    {/* Feature 2 */}
                    <div className="bg-white text-gray-900 p-8 rounded-3xl shadow-lg transform transition duration-500 hover:scale-105 hover:shadow-xl hover:bg-purple-50">
                        <div className="mb-6">
                            <svg className="w-16 h-16 text-purple-500 mx-auto" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M7 10l5 5 5-5" />
                            </svg>
                        </div>
                        <h3 className="text-2xl font-semibold mb-4">Seamless Connectivity</h3>
                        <p className="text-gray-700">
                            Stay connected during your entire trip with our seamless connectivity across transportation modes and locations.
                        </p>
                    </div>

                    {/* Feature 3 */}
                    <div className="bg-white text-gray-900 p-8 rounded-3xl shadow-lg transform transition duration-500 hover:scale-105 hover:shadow-xl hover:bg-pink-50">
                        <div className="mb-6">
                            <svg className="w-16 h-16 text-pink-500 mx-auto" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M4 6h16M4 12h16M4 18h16" />
                            </svg>
                        </div>
                        <h3 className="text-2xl font-semibold mb-4">24/7 Customer Support</h3>
                        <p className="text-gray-700">
                            Our dedicated support team is always available to assist you whenever you need help. Your journey is our priority.
                        </p>
                    </div>

                    {/* Feature 4 */}
                    <div className="bg-white text-gray-900 p-8 rounded-3xl shadow-lg transform transition duration-500 hover:scale-105 hover:shadow-xl hover:bg-blue-50">
                        <div className="mb-6">
                            <svg className="w-16 h-16 text-blue-500 mx-auto" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M3 4a2 2 0 012-2h14a2 2 0 012 2v16a2 2 0 01-2 2H5a2 2 0 01-2-2V4z" />
                            </svg>
                        </div>
                        <h3 className="text-2xl font-semibold mb-4">User-Friendly Interface</h3>
                        <p className="text-gray-700">
                            Our platform is designed to be intuitive and easy to navigate. Plan your trip effortlessly with a user-friendly interface.
                        </p>
                    </div>

                    {/* Feature 5 */}
                    <div className="bg-white text-gray-900 p-8 rounded-3xl shadow-lg transform transition duration-500 hover:scale-105 hover:shadow-xl hover:bg-purple-50">
                        <div className="mb-6">
                            <svg className="w-16 h-16 text-purple-500 mx-auto" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M14 2H10v18h4V2z" />
                            </svg>
                        </div>
                        <h3 className="text-2xl font-semibold mb-4">Affordable Pricing</h3>
                        <p className="text-gray-700">
                            We provide competitive pricing for all travel services, ensuring that your trip is both exciting and affordable.
                        </p>
                    </div>

                    {/* Feature 6 */}
                    <div className="bg-white text-gray-900 p-8 rounded-3xl shadow-lg transform transition duration-500 hover:scale-105 hover:shadow-xl hover:bg-pink-50">
                        <div className="mb-6">
                            <svg className="w-16 h-16 text-pink-500 mx-auto" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 12l2 2l4-4" />
                            </svg>
                        </div>
                        <h3 className="text-2xl font-semibold mb-4">Instant Confirmation</h3>
                        <p className="text-gray-700">
                            Get instant confirmations for your bookings, so you can rest assured that your trip is booked and ready to go.
                        </p>
                    </div>

                </div>
            </div>
        </section>
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
