import style from "./Table.module.css"; 

import { NavLink } from "react-router-dom"; 

function TableUsers({ list }) {
    return (
        <>
            <table className={style.table}>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Phone</th>
                        <th>Email</th>
                        <th>Password</th>
                        <th>Admin?</th>
                        <th>Created Timestamp</th>
                    </tr>
                </thead>
                <tbody>
                    {list && list.map((item) => (
                    <tr key={item.id}>
                        <td><NavLink to={`/users/${item.id}`}>{item.id}</NavLink></td>
                        <td>{String(item.name)}</td>
                        <td>{String(item.phone)}</td>
                        <td>{item.email}</td>
                        <td>{item.password}</td>
                        <td>{String(item.adminStatus)}</td>
                        <td>{item.createdAt}</td>
                    </tr>
                    ))}
                </tbody>
            </table>
        </>
    )
}

export default TableUsers; 