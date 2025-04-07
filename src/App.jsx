import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import AddGuestHouseForm from "./components/AddGuesthouse/AddGuestHouseForm"
import GuestHouseOverview from './components/GuestHouseOverview/GuestHouseOverview'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <GuestHouseOverview/>
    </>
  )
}

export default App