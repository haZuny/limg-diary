import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { useEffect, useState } from 'react';

import LoginPage from './pages/login_page/LoginPage';
import MainPage from './pages/main_page/MainPage';


function Router({authorized, component})  {
    // vh, vw 단위 inner 기준으로 정의
    const setScreenSize = ()=>{
        // get innerSize
        let vw = window.innerWidth * 0.01;
        let vh = window.innerHeight * 0.01;

        // set screen ratio max w:h
        let ratio_w = 11;
        let ratio_h = 16;
        vw = vw>vh*ratio_w/ratio_h ? vh*ratio_w/ratio_h : vw;

        ratio_w = 1;
        ratio_h = 2;
        vh = vh>vw*ratio_h/ratio_w ? vw*ratio_h/ratio_w : vh;

        // save css style
        document.documentElement.style.setProperty('--vh', `${vh}px`)
        document.documentElement.style.setProperty('--vw', `${vw}px`)
    }

    setScreenSize()

    // 반응형 설정
    // useEffect(()=>{
    //     // window.addEventListener('resize', setScreenSize)
    //     // return ()=>window.removeEventListener('resize', setScreenSize)
    // }, [])


    return (
    <BrowserRouter>
        <Routes>
            <Route path='/' element={<Center child={<MainPage/>}/>} />
            <Route path='/login' element={<Center child={<LoginPage/>}/>}/>
        </Routes>
    </BrowserRouter>)
}

function Center({child}){
    return (
        <div style={{
            // size
            width: `${window.innerWidth}px`,
            height : `${window.innerHeight}px`,
            // align
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            // color
            backgroundColor: '#777777'
        }}>
            {child}
        </div>
    )
}

export default Router