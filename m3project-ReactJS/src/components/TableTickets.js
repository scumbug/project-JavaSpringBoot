import style from "./Table.module.css"; 

function formatTimestamp(x) {
    return new Date(x).toLocaleString("en-SG")
}

function TableTickets({ list }) {
    return (
        <>
            <table className={style.table}>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Concert</th>
                        <th>Venue</th>
                        <th>Seat No.</th>
                        <th>Category Price</th>
                        <th>Purchased by User</th>
                        <th>Submitted?</th>
                        <th>Created Timestamp</th>
                    </tr>
                </thead>
                <tbody>
                    {list && list.map((item) => (
                    <tr key={item.ticketId}>
                        <td>{item.ticketId}</td>
                        <td>{item.concertEntity.artist}</td>
                        <td>{item.seatEntity.venueHall}</td>
                        <td>{item.seatEntity.seatId}</td>
                        <td>S${item.seatEntity.ticketPrice}</td>
                        <td>{item.userEntity.email}</td>
                        <td>{String(item.submissionStatus)}</td>
                        <td>{formatTimestamp(item.createdAt)}</td>
                    </tr>
                    ))}
                </tbody>
            </table>
        </>
    )
}

export default TableTickets; 