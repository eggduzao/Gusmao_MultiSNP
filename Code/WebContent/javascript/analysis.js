
//////////////////////////////////////////////
// VALIDATION
//////////////////////////////////////////////

function check(){
	
	var msg = "";
	
	if(document.getElementById("formAnalysis:fileupload").value=="") msg += "- Choose a BASE PATH\n";
	if(!checkIntGreat0(document.getElementById("formAnalysis:order").value) || checkFloat(document.getElementById("formAnalysis:order").value)) msg += "- Parameter ORDER must be an integer > 0\n";
	if(!checkIntGreat0(document.getElementById("formAnalysis:top").value) || checkFloat(document.getElementById("formAnalysis:top").value)) msg += "- Parameter NTOP must be an integer > 0\n";
	if(document.forms['formAnalysis']['formAnalysis:select-output'][2].checked){
		if(!checkEmail(document.getElementById("formAnalysis:emailAddress").value)) msg += "- Parameter EMAIL must be in correct mail format. Ex: somemail@somewhere.com\n";
	}
	
	//if(document.getElementById("formAnalysis:shuffle")!= null && (!checkIntGreatEqual0(document.getElementById("formAnalysis:shuffle").value) || checkFloat(document.getElementById("formAnalysis:shuffle").value))) msg += "- Parameter SHUFFLE must be an integer >= 0 \n";
	//if(document.getElementById("formAnalysis:fract")!= null && !checkFloatBetween0and1(document.getElementById("formAnalysis:fract").value)) msg += "- Parameter FRACT must be a float between 0 (not include) and 1 (include). Ex: 0.2\n";
	//if(document.getElementById("formAnalysis:ntime")!= null && (!checkIntGreat0(document.getElementById("formAnalysis:ntime").value) || checkFloat(document.getElementById("formAnalysis:ntime").value))) msg += "- Parameter NTIME must be an integer > 0\n";
	//if(document.getElementById("formAnalysis:ratsl")!= null && !checkFloatGreat1(document.getElementById("formAnalysis:ratsl").value)) msg += "- Parameter RATSL must be a float > 1. Ex: 1.4\n";
	
	//if(document.getElementById("formAnalysis:threshold")!= null && !checkFloatGreat0(document.getElementById("formAnalysis:threshold").value)) msg += "- Parameter THRESHOLD must be a float > 0. Ex: 1.0\n";
	
	if(document.getElementById("formAnalysis:shufflePia")!= null && (!checkIntGreatEqual0(document.getElementById("formAnalysis:shufflePia").value) || checkFloat(document.getElementById("formAnalysis:shufflePia").value))) msg += "- Parameter SHUFFLE (PIA) must be an integer >= 0 \n";
	if(document.getElementById("formAnalysis:fractPia")!= null && !checkFloatBetween0and1(document.getElementById("formAnalysis:fractPia").value)) msg += "- Parameter FRACT (PIA) must be a float between 0 (not include) and 1 (include). Ex: 0.2\n";
	if(document.getElementById("formAnalysis:ntimePia")!= null && (!checkIntGreat0(document.getElementById("formAnalysis:ntimePia").value) || checkFloat(document.getElementById("formAnalysis:ntimePia").value))) msg += "- Parameter NTIME (PIA) must be an integer > 0\n";
	if(document.getElementById("formAnalysis:ratslPia")!= null && !checkFloatGreat1(document.getElementById("formAnalysis:ratslPia").value)) msg += "- Parameter RATSL (PIA) must be a float > 1. Ex: 1.4\n";
	
	if(document.getElementById("formAnalysis:shuffleMdr")!= null && (!checkIntGreatEqual0(document.getElementById("formAnalysis:shuffleMdr").value) || checkFloat(document.getElementById("formAnalysis:shuffleMdr").value))) msg += "- Parameter SHUFFLE (MDR) must be an integer >= 0 \n";
	if(document.getElementById("formAnalysis:fractMdr")!= null && !checkFloatBetween0and1(document.getElementById("formAnalysis:fractMdr").value)) msg += "- Parameter FRACT (MDR) must be a float between 0 (not include) and 1 (include). Ex: 0.2\n";
	if(document.getElementById("formAnalysis:ntimeMdr")!= null && (!checkIntGreat0(document.getElementById("formAnalysis:ntimeMdr").value) || checkFloat(document.getElementById("formAnalysis:ntimeMdr").value))) msg += "- Parameter NTIME (MDR) must be an integer > 0\n";
	if(document.getElementById("formAnalysis:thresholdMdr")!= null && !checkFloatGreat0(document.getElementById("formAnalysis:thresholdMdr").value)) msg += "- Parameter THRESHOLD (MDR) must be a float > 0. Ex: 1.0\n";
	
	if(document.getElementById("formAnalysis:shuffleEsnp2")!= null && (!checkIntGreatEqual0(document.getElementById("formAnalysis:shuffleEsnp2").value) || checkFloat(document.getElementById("formAnalysis:shuffleEsnp2").value))) msg += "- Parameter SHUFFLE (ESNP2) must be an integer >= 0 \n";
	
	if(document.getElementById("formAnalysis:ratslMass")!= null && !checkFloatGreat1(document.getElementById("formAnalysis:ratslMass").value)) msg += "- Parameter RATSL (MASS) must be a float > 1. Ex: 1.4\n";
	
	if(document.getElementById("formAnalysis:useGainMass")!=null &&
	   !document.getElementById("formAnalysis:useGainMass").checked && !document.getElementById("formAnalysis:useChiMass").checked &&
	   !document.getElementById("formAnalysis:useGiniMass").checked && !document.getElementById("formAnalysis:useApdMass").checked) 
		msg+="- At least one Score must be used to evaluate the MASS Overall.\n";
	
	if(msg!=""){
		alert(msg);
		return false;
	}
	else{
		return true;
	}
}

function checkInt(n){
	return !isNaN(n);
}

function checkIntGreat0(n){
	if(isNaN(n)) return false;
	else{
		if(n > 0) return true;
		else return false;
	}
}

function checkIntGreatEqual0(n){
	if(isNaN(n)) return false;
	else{
		if(n >= 0) return true;
		else return false;
	}
}

function checkFloat(n){
	if (/\./.test(n)) {
        return true;
    } else {
        return false;
    }
}

function checkFloatGreat0(n){
	if (/\./.test(n)) {
        if(n > 0.0){
        	return true;
        }
        else{
        	return false;
        }
    } else {
        return false;
    }
}

function checkFloatGreat1(n){
	if (/\./.test(n)) {
        if(n > 1.0){
        	return true;
        }
        else{
        	return false;
        }
    } else {
        return false;
    }
}

function checkFloatBetween0and1(n){
	if (/\./.test(n)) {
        if(n > 0.0 && n <= 1.0){
        	return true;
        }
        else{
        	return false;
        }
    } else {
        return false;
    }
}

function checkEmail(email){
	var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
	if(reg.test(email) == false){
	    return false;
	}
	else{
		return true;
	}
}

//////////////////////////////////////////////
// HIGHIGHT
//////////////////////////////////////////////

function highlight(){
	img = document.getElementById("parameter-title").style.backgroundImage;
	img = getFileNameFromPath(img);
	if(img == "expand.png" || img == ""){
		document.getElementById("parameter-title").style.backgroundImage = "url(../img/analysis/expandcolor.png)";
	}
	else{
		document.getElementById("parameter-title").style.backgroundImage = "url(../img/analysis/collapsecolor.png)";
	}
	document.getElementById("parameter-title").style.backgroundColor="#ebe7fd";
}

function fadelight(){
	img = document.getElementById("parameter-title").style.backgroundImage;
	img = getFileNameFromPath(img);
	if(img == "expandcolor.png"){
		document.getElementById("parameter-title").style.backgroundImage = "url(../img/analysis/expand.png)";
	}
	else{
		document.getElementById("parameter-title").style.backgroundImage = "url(../img/analysis/collapse.png)";
	}
	document.getElementById("parameter-title").style.backgroundColor="#ffffff";
}

function openOrCloseParameters(){
	img = document.getElementById("parameter-title").style.backgroundImage;
	img = getFileNameFromPath(img);
	if(img == "expand.png"){
		document.getElementById("parameter-title").style.backgroundImage = "url(../img/analysis/collapse.png)";
		if(document.getElementById("pia-parameter")!=null){
			document.getElementById("pia-parameter").style.display="";
			document.getElementById("pia-analysis").style.height="530px";
		}
		else if(document.getElementById("mdr-parameter")!=null){
			document.getElementById("mdr-parameter").style.display="";
			document.getElementById("mdr-analysis").style.height="530px";
		}
		else if(document.getElementById("esnp2-parameter")!=null){
			document.getElementById("esnp2-parameter").style.display="";
			document.getElementById("esnp2-analysis").style.height="470px";
		}
		else if(document.getElementById("mass-parameter")!=null){
			document.getElementById("mass-parameter").style.display="";
			document.getElementById("mass-analysis").style.height="530px";
		}
		else{
			document.getElementById("comparative-parameter").style.display="";
			document.getElementById("comparative-analysis").style.height="940px";
		}
	}
	else if(img == "expandcolor.png"){
		document.getElementById("parameter-title").style.backgroundImage = "url(../img/analysis/collapsecolor.png)";
		if(document.getElementById("pia-parameter")!=null){
			document.getElementById("pia-parameter").style.display="";
			document.getElementById("pia-analysis").style.height="530px";
		}
		else if(document.getElementById("mdr-parameter")!=null){
			document.getElementById("mdr-parameter").style.display="";
			document.getElementById("mdr-analysis").style.height="530px";
		}
		else if(document.getElementById("esnp2-parameter")!=null){
			document.getElementById("esnp2-parameter").style.display="";
			document.getElementById("esnp2-analysis").style.height="470px";
		}
		else if(document.getElementById("mass-parameter")!=null){
			document.getElementById("mass-parameter").style.display="";
			document.getElementById("mass-analysis").style.height="530px";
		}
		else{
			document.getElementById("comparative-parameter").style.display="";
			document.getElementById("comparative-analysis").style.height="940px";
		}
	}
	else if(img == "collapse.png"){
		document.getElementById("parameter-title").style.backgroundImage = "url(../img/analysis/expand.png)";
		if(document.getElementById("pia-parameter")!=null){
			document.getElementById("pia-parameter").style.display="none";
			document.getElementById("pia-analysis").style.height="370px";
		}
		else if(document.getElementById("mdr-parameter")!=null){
			document.getElementById("mdr-parameter").style.display="none";
			document.getElementById("mdr-analysis").style.height="370px";
		}
		else if(document.getElementById("esnp2-parameter")!=null){
			document.getElementById("esnp2-parameter").style.display="none";
			document.getElementById("esnp2-analysis").style.height="370px";
		}
		else if(document.getElementById("mass-parameter")!=null){
			document.getElementById("mass-parameter").style.display="none";
			document.getElementById("mass-analysis").style.height="370px";
		}
		else{
			document.getElementById("comparative-parameter").style.display="none";
			document.getElementById("comparative-analysis").style.height="370px";
		}
	}
	else{
		document.getElementById("parameter-title").style.backgroundImage = "url(../img/analysis/expandcolor.png)";
		if(document.getElementById("pia-parameter")!=null){
			document.getElementById("pia-parameter").style.display="none";
			document.getElementById("pia-analysis").style.height="370px";
		}
		else if(document.getElementById("mdr-parameter")!=null){
			document.getElementById("mdr-parameter").style.display="none";
			document.getElementById("mdr-analysis").style.height="370px";
		}
		else if(document.getElementById("esnp2-parameter")!=null){
			document.getElementById("esnp2-parameter").style.display="none";
			document.getElementById("esnp2-analysis").style.height="370px";
		}
		else if(document.getElementById("mass-parameter")!=null){
			document.getElementById("mass-parameter").style.display="none";
			document.getElementById("mass-analysis").style.height="370px";
		}
		else{
			document.getElementById("comparative-parameter").style.display="none";
			document.getElementById("comparative-analysis").style.height="370px";
		}
	}
}

function showEmail(){
	if(document.forms['formAnalysis']['formAnalysis:select-output'][2].checked){
		document.getElementById("emailSelection").style.display="";
	}
	else{
		document.getElementById("emailSelection").style.display="none";
	}
}

//////////////////////////////////////////////
// HELP
//////////////////////////////////////////////

// General

function helpBasePath(){
	alert("Select the file of the base you wish to analyze");
}

function helpOrder(){
	alert("The order of interaction");
}

function helpTopResults(){
	alert("The number of top combinations to be displayed");
}

function helpPathwayUsage(){
	alert("Select this option if your base have pathway information");
}

function helpOutput(){
	alert("Select the way you wish to receive the results");
}

function helpEmail(){
	alert("Type the email address you wish to receive the results");
}

// PIA

function helpShufflePIA(){
	alert("Number of times the data will be shuffled at the start of each cross-validation step");
}

function helpFractPIA(){
	alert("Represents how much of the data will be used as the test data in the cross-validation analysis. It must be a float between 0 and 1. Ex: If 0.1 is passed, then 10% of the data will be used as test at each cross validation interval testing");
}

function helpNtimePIA(){
	alert("Tells how much cross-validations repetitions will be performed. The TP, FP, TN, FN numbers are summed for all the cross-validation repetitions in each feature combination");
}

function helpRatslPIA(){
	alert("It's a floating value that cut off some attribute combinations that does not satisfy the following criteria: Max(cases,controls) / Min(cases,controls) <= Ratsl. It is used to cut the combinations extremely affected by missing data");
}

function helpIfractPIA(){
	alert("Must be 0 or 1. Where 1 means that we are using fractional occupation to populate the genotype-phenotype table and 0 means that we use the actual values");
}

function helpItrainPIA(){
	alert("Must be 0 or 1. If 1 it means that we are using the training set to calculate the contingency table (along with the testing data), if 0 it means that we are using only the testing data");
}

function helpLootrPIA(){
	alert("If the ITrain parameter is 1, it tells how the training set values are used to populate the contingency table. For detailed information on each possibility see reference [2] on session \"References\"");
}

// MDR

function helpShuffleMDR(){
	alert("Number of times the data will be shuffled at the start of each cross-validation step");
}

function helpFractMDR(){
	alert("Represents how much of the data will be used as the test data in the cross-validation analysis. It must be a float between 0 and 1. Ex: If 0.1 is passed, then 10% of the data will be used as test at each cross validation interval testing");
}

function helpNtimeMDR(){
	alert("Tells how much cross-validations repetitions will be performed. The TP, FP, TN, FN numbers are summed for all the cross-validation repetitions in each feature combination");
}

function helpTMDR(){
	alert("A floating value representing the threshold used on the categorization of each cell of the phenotype-genotype table as \"low-risk\" and \"high-risk\"");
}

// ESNP2

function helpShuffleESNP2(){
	alert("Shuffles the individuals of the database for Bootstrapping algorithm");
}

//MASS

function helpRatslMASS(){
	alert("It's a floating value that cut off some attribute combinations that does not satisfy the following criteria: Max(cases,controls) / Min(cases,controls) <= Ratsl. It is used to cut the combinations extremely affected by missing data");
}

function helpAssignMASS(){
	alert("Defines the usage of only individual attribute's scores for the combination's scores (true) or the usage of all attribute's possible combinations (false)");
}

function helpUseGainMASS(){
	alert("Defines the usage of Information Gain metric on Overall Score");
}

function helpUseChiMASS(){
	alert("Defines the usage of Chi-Square metric on Overall Score");
}

function helpUseGiniMASS(){
	alert("Defines the usage of Gini Index metric on Overall Score");
}

function helpUseApdMASS(){
	alert("Defines the usage of APD metric on Overall Score");
}

//////////////////////////////////////////////
// AUXILIARY
//////////////////////////////////////////////

function getFileNameFromPath(path){
	var name = "";
	var start = false;
	for(i=path.length-2;i>=0;i--){
		var c = path.charAt(i);
		if(c == "/") break;
		if(c == "g") start = true;
		if(start) name = c + name;
	}
	return name;
}
