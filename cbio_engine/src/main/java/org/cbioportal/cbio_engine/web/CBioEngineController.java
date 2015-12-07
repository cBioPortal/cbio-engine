/*
 * This file is part of cBioPortal.
 *
 * cBioPortal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.cbioportal.cbio_engine.web;

import org.cbioportal.cbio_engine.domain.*;
import org.cbioportal.cbio_engine.service.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Benjamin Gross
 */
@RestController // shorthand for @Controller, @ResponseBody
@RequestMapping(value = "/cbio_engine/")
public class CBioEngineController
{
    @Autowired
    public DataAccessService dataAccessService;
    
    @RequestMapping(value = "/clinical/sample/{sampleId}", method = RequestMethod.GET)
    public ClinicalRecord getSampleClinical(@PathVariable String sampleId)
    {
        ClinicalRecord sampleRecord = dataAccessService.getClinicalRecordBySampleId(sampleId);

        return sampleRecord;
    }
}
