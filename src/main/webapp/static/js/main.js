jQuery(document).ready(function($) {
			// browser window scroll (in pixels) after which the "back to top"
			// link is shown
			var offset = 300,
			// browser window scroll (in pixels) after which the "back to top"
			// link opacity is reduced
			offset_opacity = 1200,
			// duration of the top scrolling animation (in ms)
			scroll_top_duration = 700,
			// grab the "back to top" link
			$back_to_top = $('.back-to-top');

			// hide or show the "back to top" link
			$(window).scroll(
					function() {
						($(this).scrollTop() > offset) ? $back_to_top
								.addClass('cd-is-visible') : $back_to_top
								.removeClass('cd-is-visible cd-fade-out');
						if ($(this).scrollTop() > offset_opacity) {
							$back_to_top.addClass('cd-fade-out');
						}
					});

			// smooth scroll to top
			$back_to_top.on('click', function(event) {
				event.preventDefault();
				$('body,html').animate({
					scrollTop : 0,
				}, scroll_top_duration);
			});
			
			
			/** *** DataTabels **** */
			$("#managePharmacy").DataTable({
				"aoColumns" : [ null, null, {
					"asSorting" : false
				}, {
					"asSorting" : false
				} ]
			});
			$("#manageUser").DataTable({
				"aoColumns" : [ null, null, {
					"asSorting" : false
				},null,{
					"asSorting" : false
				}, {
					"asSorting" : false
				} ]
			});
			$("#listOfAuthorizationCodes").DataTable({
				"aoColumnDefs" : [ {
					"bSortable" : false,
					"aTargets" : [ "no-sort" ]
					} ]
			});

			
			/** *** Bootstrap Tooltip **** */
			$('[data-toggle="tooltip"]').tooltip({
				placement : "top"
			});

			/** *** Form validation **** */
			$("#authorizationForm").validate({
				tooltip_options : {
					prescriberID : {
						placement : 'top'
					},
					patientID : {
						placement : 'top'
					},
					birthMonth : {
						placement : 'top'
					},
					birthDay : {
						placement : 'top'
					}
				}
			});
			$("#createUserForm").validate({
				tooltip_options : {
					email : {
						placement : 'top'
					}
				}
			});
			$("#createPharmacyForm").validate({
				tooltip_options : {
					pharmacyName : {
						placement : 'top'
					}
				}
			});
			$("#patientIdForm").validate({
				tooltip_options : {
					patientId : {
						placement : 'top'
					}
				}
			});
			$("#forgotPasswordForm").validate({
				tooltip_options : {
					email : {
						placement : 'top'
					}
				}
			});
			$("#setPasswordForm").validate({
				tooltip_options : {
					password : {
						placement : 'top'
					},
					confirmPassword : {
						placement : 'top'
					},
				}
			});

		});