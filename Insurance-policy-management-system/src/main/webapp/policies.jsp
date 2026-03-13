<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="header.jsp" %>

<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2>All Policies</h2>
        <a class="btn btn-success" href="policyForm">Create Policy</a>
    </div>

    <div id="alertMsg"></div>

    <div id="policies-container">
        <div class="alert alert-info">Loading policies...</div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>
$(document).ready(function () {
    loadPolicies();
});

function loadPolicies() {
    $.ajax({
        url: 'getPolicies',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            var container = $('#policies-container');

            if (data.status === 'empty') {
                container.html(
                    '<div class="alert alert-info text-center mt-4">' +
                    '<h5>No Policies Available</h5>' +
                    '<p>There are no policies added yet.</p>' +
                    '<a href="policyForm" class="btn btn-success">Create New Policy</a>' +
                    '</div>'
                );
                return;
            }

            if (data.policyList && data.policyList.length > 0) {
                var html = '<table class="table table-striped table-bordered">';
                html += '<thead class="table-dark"><tr>';
                html += '<th>ID</th><th>Policy Name</th><th>Type</th>';
                html += '<th>Coverage</th><th>Premium</th><th>Duration (Months)</th>';
                html += '<th>Status</th><th>Actions</th>';
                html += '</tr></thead><tbody>';

                $.each(data.policyList, function (i, policy) {
                    var statusBadge = policy.active
                        ? '<span class="badge bg-success">Active</span>'
                        : '<span class="badge bg-secondary">Inactive</span>';

                    var deleteBtn = policy.active
                        ? '<button class="btn btn-danger btn-sm ms-1" onclick="deletePolicy(' + policy.policyId + ')">Delete</button>'
                        : '';

                    html += '<tr id="row-' + policy.policyId + '">';
                    html += '<td>' + policy.policyId + '</td>';
                    html += '<td>' + policy.policyName + '</td>';
                    html += '<td>' + policy.policyType + '</td>';
                    html += '<td>' + policy.coverageAmount + '</td>';
                    html += '<td>' + policy.premiumAmount + '</td>';
                    html += '<td>' + policy.durationMonths + '</td>';
                    html += '<td>' + statusBadge + '</td>';
                    html += '<td>' + deleteBtn + '</td>';
                    html += '</tr>';
                });

                html += '</tbody></table>';
                container.html(html);

            } else {
                container.html(
                    '<div class="alert alert-info text-center mt-4">' +
                    '<h5>No Policies Available</h5>' +
                    '<p>There are no policies added yet.</p>' +
                    '<a href="policyForm" class="btn btn-success">Create New Policy</a>' +
                    '</div>'
                );
            }
        },
        error: function () {
            $('#policies-container').html('<div class="alert alert-danger">Failed to load policies. Please try again.</div>');
        }
    });
}

function deletePolicy(policyId) {
    if (!confirm('Are you sure you want to delete this policy?')) return;

    $.ajax({
        url: 'deletePolicy',
        type: 'POST',
        dataType: 'json',
        data: { policyId: policyId },
        success: function (response) {
            if (response.status === 'success') {
                $('#row-' + policyId).fadeOut(400, function () {
                    $(this).remove();
                });
                showAlert('success', 'Policy deleted successfully.');
                // if table is now empty show the no policies message
                if ($('#policies-container tbody tr').length === 0) {
                    $('#policies-container').html(
                        '<div class="alert alert-info text-center mt-4">' +
                        '<h5>No Policies Available</h5>' +
                        '<p>There are no policies added yet.</p>' +
                        '<a href="policyForm" class="btn btn-success">Create New Policy</a>' +
                        '</div>'
                    );
                }
            } else {
                showAlert('danger', response.message || 'Delete failed.');
            }
        },
        error: function () {
            showAlert('danger', 'Failed to delete policy. Please try again.');
        }
    });
}

function showAlert(type, msg) {
    $('#alertMsg').html(
        '<div class="alert alert-' + type + ' alert-dismissible fade show">' +
        msg + '<button type="button" class="btn-close" data-bs-dismiss="alert"></button></div>'
    );
}
</script>

<%@ include file="footer.jsp" %>