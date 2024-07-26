import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { useEffect, useState } from 'react';
import LoginPage from './pages/login_page/LoginPage';

const Background = ({child})=>{

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
        <div style={{width:`${width}px`, height:`${height}px`}}>
            {child}
        </div>
    )
}

function Router({authorized, component})  {       

    return (
    <BrowserRouter>
        <Routes>
            <Route path='/' element={<Background child={'안녕'}/>} />
            <Route path='/login' element={<Background child={<LoginPage/>}/>}/>
        </Routes>
    </BrowserRouter>)
}

export default Router