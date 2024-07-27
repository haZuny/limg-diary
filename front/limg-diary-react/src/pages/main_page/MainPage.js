import css from './MainPage.module.scss'

import WhiteBox from '../global_component/white_box/WhiteBox'


function MainPage(){
    return (
        <div id={css.root_container} className={css.container}>
            <WhiteBox child={"asdfasd"}></WhiteBox>
        </div>
    )
}

export default MainPage