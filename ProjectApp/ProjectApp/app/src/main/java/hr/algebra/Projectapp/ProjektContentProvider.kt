package hr.algebra.Projectapp

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import hr.algebra.Projectapp.dao.Repository
import hr.algebra.Projectapp.factory.getProjectRepository
import hr.algebra.Projectapp.model.Item

private const val AUTHORITY = "hr.algebra.Projectapp.api.provider"
private const val PATH = "items"
val PROVIDER_CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$PATH")

private val URI_MATCHER = with(UriMatcher(UriMatcher.NO_MATCH)) {
    addURI(AUTHORITY, PATH, ITEMS) //content://hr.algebra.Projectapp.api.provider/items
    addURI(AUTHORITY, "$PATH/#", ITEM_ID) //content://hr.algebra.Projectapp.api.provider/items/5
    this
}

private const val ITEMS = 10
private const val ITEM_ID = 20




class NasaContentProvider : ContentProvider() {

    private lateinit var repository: Repository

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        when(URI_MATCHER.match(uri)) {
            ITEMS -> return repository.delete(selection, selectionArgs)
            ITEM_ID -> {
                uri.lastPathSegment?.let {
                    return repository.delete(
                        "${Item::_id.name}=?",
                        arrayOf(it)
                    )
                }
            }
        }
        throw IllegalArgumentException("WRONG URI")
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val id = repository.insert(values)
        return ContentUris.withAppendedId(PROVIDER_CONTENT_URI, id)
    }

    override fun onCreate(): Boolean {
        repository = getProjectRepository(context)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? = repository.query(
        projection,
        selection,
        selectionArgs,
        sortOrder
    )

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        when(URI_MATCHER.match(uri)) {
            ITEMS -> return repository.update(values, selection, selectionArgs)
            ITEM_ID -> {
                uri.lastPathSegment?.let {
                    return repository.update(
                        values,
                        "${Item::_id.name}=?",
                        arrayOf(it)
                    )
                }
            }
        }
        throw IllegalArgumentException("WRONG URI")

    }
}