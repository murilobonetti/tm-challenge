package com.mbonetti.tmchallenge

import com.mbonetti.tmchallenge.db.models.Address
import com.mbonetti.tmchallenge.db.models.City
import com.mbonetti.tmchallenge.db.models.EmbeddedVenue
import com.mbonetti.tmchallenge.db.models.EventImage
import com.mbonetti.tmchallenge.db.models.State
import com.mbonetti.tmchallenge.db.models.Venue
import com.mbonetti.tmchallenge.db.util.Converters
import org.junit.Assert.assertEquals
import org.junit.Test

class ConvertersTest {

    private val converters = Converters()

    @Test
    fun testFromJsonToVenue() {
        val venueString = "{\n" +
                "    \"venues\": [\n" +
                "        {\n" +
                "            \"name\": \"Footprint Center\",\n" +
                "            \"id\": \"KovZpZAE617A\",\n" +
                "            \"url\": \"https://www.ticketmaster.com/footprint-center-tickets-phoenix/venue/205079\",\n" +
                "            \"city\": {\n" +
                "                \"name\": \"Phoenix\"\n" +
                "            },\n" +
                "            \"state\": {\n" +
                "                \"name\": \"Arizona\",\n" +
                "                \"stateCode\": \"AZ\"\n" +
                "            },\n" +
                "            \"address\": {\n" +
                "                \"line1\": \"201 East Jefferson Street\"\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "}"
        val expectedVenue = EmbeddedVenue(
            listOf(
                Venue(
                    id = "KovZpZAE617A",
                    name = "Footprint Center",
                    url = "https://www.ticketmaster.com/footprint-center-tickets-phoenix/venue/205079",
                    city = City(name = "Phoenix"),
                    state = State(name = "Arizona", stateCode = "AZ"),
                    address = Address(line1 = "201 East Jefferson Street")
                )
            )
        )

        val result = converters.fromJsonToVenue(venueString)

        assertEquals(expectedVenue, result)
    }

    @Test
    fun testFromVenueToJson() {
        val venue = EmbeddedVenue(
            listOf(
                Venue(
                    name = "Footprint Center",
                    id = "KovZpZAE617A",
                    url = "https://www.ticketmaster.com/footprint-center-tickets-phoenix/venue/205079",
                    city = City(name = "Phoenix"),
                    state = State(name = "Arizona", stateCode = "AZ"),
                    address = Address(line1 = "201 East Jefferson Street")
                )
            )
        )

        val expectedVenueString = "{\"venues\":[{\"id\":\"KovZpZAE617A\",\"name\":\"Footprint Center\",\"address\":{\"line1\":\"201 East Jefferson Street\"},\"city\":{\"name\":\"Phoenix\"},\"state\":{\"name\":\"Arizona\",\"stateCode\":\"AZ\"},\"url\":\"https://www.ticketmaster.com/footprint-center-tickets-phoenix/venue/205079\"}]}"
        val result = converters.fromVenueToJson(venue)

        assertEquals(expectedVenueString, result)
    }

    @Test
    fun testFromJsonImageList() {
        val imageString = "[{\"url\":\"https://s1.ticketm.net/dam/a/c62/0636ff21-e369-4b8c-bee4-214ea0a81c62_1339761_CUSTOM.jpg\",\"ratio\":\"4_3\",\"fallback\":false,\"height\":225,\"width\":305}]"

        val image = EventImage(
                ratio = "4_3",
                url = "https://s1.ticketm.net/dam/a/c62/0636ff21-e369-4b8c-bee4-214ea0a81c62_1339761_CUSTOM.jpg",
                width = 305,
                height = 225,
                fallback = false
        )
        val expectedImageList = listOf(image)

        val result = converters.fromJsonImageList(imageString)

        assertEquals(expectedImageList, result)
    }

    @Test
    fun testFromImageListToJson() {
        val image = EventImage(
            ratio = "4_3",
            url = "https://s1.ticketm.net/dam/a/c62/0636ff21-e369-4b8c-bee4-214ea0a81c62_1339761_CUSTOM.jpg",
            width = 305,
            height = 225,
            fallback = false
        )
        val imageList = listOf(image)
        val expectedImageString = "[{\"url\":\"https://s1.ticketm.net/dam/a/c62/0636ff21-e369-4b8c-bee4-214ea0a81c62_1339761_CUSTOM.jpg\",\"ratio\":\"4_3\",\"fallback\":false,\"height\":225,\"width\":305}]"

        val result = converters.fromImageListToJson(imageList)

        assertEquals(expectedImageString, result)
    }
}
