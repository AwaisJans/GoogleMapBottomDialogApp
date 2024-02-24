package com.jans.googlemap.cut.edge.app.model.urlDetailsMarker


data class SingleItem(
    val beschreibung: String,
    val bezeichnung: String,
    val bild: Bild,
    val bilder: List<Bild>,
    val dateien: List<Any>, // Define the class for this if needed
    val id: Int,
    val kategorien: List<Kategorie>,
    val icon: Icon,
    val data: Data,
    val urlPopUp: String,
    val urlDetails: String,
    val isDetailsWebsite: Boolean
)