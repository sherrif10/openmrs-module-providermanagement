/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.providermanagement.fragment.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.providermanagement.ProviderRole;
import org.openmrs.module.providermanagement.api.ProviderManagementService;
import org.openmrs.module.providermanagement.api.ProviderSuggestionService;
import org.openmrs.module.providermanagement.suggestion.SupervisionSuggestion;
import org.openmrs.module.providermanagement.suggestion.SupervisionSuggestionType;
import org.openmrs.ui.framework.annotation.BindParams;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class SupervisionSuggestionFormFragmentController {

    public void controller(FragmentModel model,
                           @FragmentParam(value = "supervisionSuggestion", required = false) SupervisionSuggestion suggestion) {

        // hard code the evaluator to groovy evaluator since this is the only type we currently support
        if (suggestion == null) {
            suggestion = new SupervisionSuggestion();
            suggestion.setEvaluator("org.openmrs.module.providermanagement.suggestion.GroovySuggestionEvaluator");
        }

        model.addAttribute("supervisionSuggestion", suggestion);

        // add possible provider roles
        List<ProviderRole> providerRoles = Context.getService(ProviderManagementService.class).getAllProviderRoles(false);
        model.addAttribute("providerRoles", providerRoles);

        // add the possible suggestion types
        model.addAttribute("suggestionTypes", SupervisionSuggestionType.values());

    }

    public void deleteSupervisionSuggestion(@RequestParam(value = "supervisionSuggestion", required = true) SupervisionSuggestion supervisionSuggestion) {

        // TODO: add some validation/sanity check here

        Context.getService(ProviderSuggestionService.class).purgeSupervisionSuggestion(supervisionSuggestion);

    }

    public void retireSupervisionSuggestion(@RequestParam(value = "supervisionSuggestion", required = true) SupervisionSuggestion supervisionSuggestion) {

        // TODO: add some validation/sanity check here

        Context.getService(ProviderSuggestionService.class).retireSupervisionSuggestion(supervisionSuggestion, "retired via provider management ui");
    }


    public void saveSupervisionSuggestion(@BindParams() SupervisionSuggestion suggestion) {

        // TODO: add validation -- at least check that the criteria is valid!

        // hard code the evaluator to the groovy evaluator since this is the only type we currently support
        suggestion.setEvaluator("org.openmrs.module.providermanagement.suggestion.GroovySuggestionEvaluator");
        Context.getService(ProviderSuggestionService.class).saveSupervisionSuggestion(suggestion);
    }

}
