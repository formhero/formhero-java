formhero.setFormHeroHost('formhero.cloud');
formhero.useHttps(true);

function launchFormFromJava(org, team, form, dataMap) {
    if (!dataMap) dataMap = {};
    formhero.loadForm({
        organization: org,
        team: team,
        form: form,
        onCloseFn: function (status) {
            window.hostApplication.onClose(status);
        },
        onStatusFn: function (status) {
            window.hostApplication.onFormProgress(status);
        }
    }, dataMap).then(
        function (successResult) {
            window.hostApplication.onFormSubmission(successResult);
        },
        function (cancelledResult) {
            window.hostApplication.onFormCancel(cancelledResult);
        }
    ).catch(function (exceptionResult) {
        window.hostApplication.onFormCancel(exceptionResult);
    });
}