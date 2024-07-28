import css from './WhiteBox.module.scss'

function WhiteBox({title, child}){
    return (
        <div id={css.root_container} className={css.container}>
            {/* 타이틀 */}
            <div id={css.title}>{title}</div>
            {/* 박스 */}
            <div id={css.box} className={css.container}>
                {child}
            </div>
        </div>
        
    )
}

export default WhiteBox