package sg.edu.ntu.m3project.m3project.repository

import org.springframework.data.repository.CrudRepository
import sg.edu.ntu.m3project.m3project.entity.ConcertEntity
import java.sql.Timestamp

interface ConcertRepository : CrudRepository<ConcertEntity?, Int?> {
    fun findByConcertDateAfter(concertDate: Timestamp?): List<ConcertEntity?>?
    fun findByArtist(artist: String?): List<ConcertEntity?>?
    fun findByArtistContaining(artist: String?): List<ConcertEntity?>?
}