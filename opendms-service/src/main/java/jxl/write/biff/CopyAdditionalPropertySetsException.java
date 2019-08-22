/*********************************************************************
 * Copyright (C) 2002 Andrew Khan
 * <p>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * <p>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 ***************************************************************************/

package jxl.write.biff;

/**
 * Exception thrown when attempting to copy a workbook which contains 
 * additional property sets
 */
public class CopyAdditionalPropertySetsException extends JxlWriteException {
    /**
     * Constructor
     */
    public CopyAdditionalPropertySetsException() {
        super(copyPropertySets);
    }
}
