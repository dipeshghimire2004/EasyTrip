import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import AddGuestHouseForm from "./components/AddGuesthouse/AddGuestHouseForm"

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <AddGuestHouseForm/>
    </>
  )
}

export default App