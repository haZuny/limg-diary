import css from './WhiteBox.module.scss'

function WhiteBox({child}){
    return (
        <div id={css.box} className={css.container}>
            {child}
        </div>
    )
}

export default WhiteBox