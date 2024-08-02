import logo from './logo.svg';
import Router from './Router'
import { useEffect, useState } from 'react';


function App() {
  const [width, setWidth] = useState(window.innerWidth)
  const [height, setHeight] = useState(window.innerHeight)

  const resizeHandler = (()=>{
      setWidth(window.innerWidth)
      setHeight(window.innerHeight)
  })

  useEffect(()=>{
      window.addEventListener('resize', resizeHandler)
      return ()=>window.removeEventListener('resize', resizeHandler)
  }, [])


  return (
    <Router style={{width:`${width}px`, height:`${height}px`, backgroundColor: 'red'}}/>
  );
}

export default App;
