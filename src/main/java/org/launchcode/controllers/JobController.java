package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job job = this.jobData.findById(id);

        model.addAttribute("title", "Job with ID: " + id);
        model.addAttribute("job", job);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        if (errors.hasErrors())
        {
            model.addAttribute(new JobForm());
            return "new-job";
        }

        int new_employerId = jobForm.getEmployerId();
        int new_locationId = jobForm.getLocationId();
        int new_coreCompetencyId = jobForm.getCoreCompetencyId();
        int new_positionTypeId = jobForm.getPositionTypeId();

        Employer job_employer = jobData.getEmployers().findById(new_employerId);
        Location job_location = jobData.getLocations().findById(new_locationId);
        CoreCompetency job_coreCompetency = jobData.getCoreCompetencies().findById(new_coreCompetencyId);
        PositionType job_positionType = jobData.getPositionTypes().findById(new_positionTypeId);

        Job new_job = new Job(jobForm.getName(), job_employer, job_location, job_positionType, job_coreCompetency);

        jobData.add(new_job);

        return "redirect:/job?id=" + new_job.getId();

    }
}
