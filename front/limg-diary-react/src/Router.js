import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { useEffect, useState } from 'react';
import LoginPage from './pages/login_page/LoginPage';

const Background = ({child})=>{

    // vh, vw 단위 inner 기준으로 정의
    const setScreenSize = ()=>{
        const vh = window.innerHeight * 0.01;
        const vw = window.innerWidth * 0.01;
        document.documentElement.style.setProperty('--vh', `${vh}px`)
        document.documentElement.style.setProperty('--vw', `${vw}px`)
    }

    useEffect(()=>{
        window.addEventListener('resize', setScreenSize)
        return ()=>window.removeEventListener('resize', setScreenSize)
    }, [])

    return (
        <div>
            {child}
        </div>
    )
}

function Router({authorized, component})  {
    // set inner size 
    const vh = window.innerHeight * 0.01;
    const vw = window.innerWidth * 0.01;
    document.documentElement.style.setProperty('--vh', `${vh}px`)
    document.documentElement.style.setProperty('--vw', `${vw}px`)    

    return (
    <BrowserRouter>
        <Routes>
            <Route path='/' element={<Background child={'안녕'}/>} />
            <Route path='/login' element={<Background child={<LoginPage/>}/>}/>
        </Routes>
    </BrowserRouter>)
}

export default Router