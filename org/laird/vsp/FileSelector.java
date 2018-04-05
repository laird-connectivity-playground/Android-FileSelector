/******************************************************************************
** Copyright (C) 2018 Laird
**
** Project: FileSelector
**
** Module: fileselector.java
**
** Notes:
**
** License: This program is free software: you can redistribute it and/or
**          modify it under the terms of the GNU General Public License as
**          published by the Free Software Foundation, version 3.
**
**          This program is distributed in the hope that it will be useful,
**          but WITHOUT ANY WARRANTY; without even the implied warranty of
**          MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
**          GNU General Public License for more details.
**
**          You should have received a copy of the GNU General Public License
**          along with this program.  If not, see http://www.gnu.org/licenses/
**
*******************************************************************************/
package org.laird.vsp;

/******************************************************************************/
// Imports
/******************************************************************************/
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/******************************************************************************/
// Class definitions
/******************************************************************************/
public class FileSelector
{
    @SuppressWarnings("WeakerAccess")
    Context qtContext = null;

    @SuppressWarnings("WeakerAccess")
    public FileSelector()
    {
    }
    public FileSelector(Context cContext)
    {
        //Set the context to the Qt context
        this();
        qtContext = cContext;
    }

    public byte[] readFileContents(Uri uriFilename)
    {
        //Read data from a file
        byte[] baFileData = null;
        try
        {
            //Get the file data
            baFileData = readDataFromUri(qtContext, uriFilename);
        }
        catch (Exception e)
        {
        }

        //Return the byte array data
        return baFileData;
    }

    private byte[] readDataFromUri(Context cContext, Uri uriUri) throws IOException
    {
        //Attempt to read data from a Uri
        InputStream isStream = cContext.getContentResolver().openInputStream(uriUri);
        ByteArrayOutputStream baosArrayOut = new ByteArrayOutputStream();

        while (isStream.available() > 0)
        {
            //Read until end of file
            baosArrayOut.write(isStream.read());
        }

        //Close the stream and return the byte array
        isStream.close();
        return baosArrayOut.toByteArray();
    }

    public String getFileName(Uri uriFilename)
    {
        //Retrieve the filename of supported content resolvers
        String strFilename = null;
        Cursor cuCursor = qtContext.getContentResolver().query(uriFilename, new String[] { MediaStore.Files.FileColumns.DISPLAY_NAME }, null, null, null);
        if (cuCursor != null)
        {
            //Cursor exists, check row count
            if (cuCursor.getCount() > 0)
            {
                //Row exists, move to this row and check column count
                cuCursor.moveToFirst();
                if (cuCursor.getColumnCount() > 0)
                {
                    //Column exists, get the filename
                    strFilename = cuCursor.getString(0);
                }
            }

            //Close the cursor
            cuCursor.close();
        }

        //Return the filename (or null if it wasn't found)
        return strFilename;
    }
}
