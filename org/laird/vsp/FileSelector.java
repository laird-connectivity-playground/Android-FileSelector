/******************************************************************************
** Copyright (C) 2015-2017 Laird
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

    public byte[] readFileContents(Uri Filename)
    {
        //Read data from a file
        byte[] baFileData = null;
        try
        {
            //Get the file data
            baFileData = readDataFromUri(qtContext, Filename);
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
}
