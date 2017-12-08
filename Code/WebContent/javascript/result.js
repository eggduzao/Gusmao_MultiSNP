
/**
 * This functions ordering is based on the fitness, so it may appear scrambled for each method
 */

function renderPathwayPIA(){
	
	if(document.getElementById("showPathway").value=="true"){
	
		// Fixing Elements
		document.getElementById("pia-result").style.height="1450px";
		
		// Displaying Pathway Containers
		document.getElementById("full-container-pathway").style.display="";
	
	}
	
}

function renderPathwayMDR(){
	
	if(document.getElementById("showPathway").value=="false"){
		
		// Fixing Elements
		document.getElementById("third-spacing").style.width="60px";
		document.getElementById("third-spacing-removepathwaygap").style.display="";
		
		// Displaying Pathway Containers
		document.getElementById("third-container-pathway").style.display="none";
	
	}
	
}

function renderPathwayESNP2(){
	
	if(document.getElementById("showPathway").value=="false"){
		
		// Fixing Elements
		document.getElementById("half-spacing-removepathwaygap").style.display="";
		
		// Displaying Pathway Containers
		document.getElementById("half-container-pathway").style.display="none";
	
	}
	
}

function renderPathwayMASS(){
	
	if(document.getElementById("showPathway").value=="true"){
	
		// Fixing Elements
		document.getElementById("mass-result").style.height="1450px";
		
		// Displaying Pathway Containers
		document.getElementById("full-container-pathway").style.display="";
	
	}
	
}

function renderPathwayComparative(){
	
	if(document.getElementById("showPathway").value=="false"){
		
		// Fixing Elements
		document.getElementById("half-spacing-removepathwaygap").style.display="";
		
		// Displaying Pathway Containers
		document.getElementById("half-container-pathway").style.display="none";
	
	}
	
}

function renderPathway(){
	
	if(document.getElementById("showPathway").value=="true"){
		
		// Fixing elements
		
		// PIA
		if(document.getElementById("pia-result")!=null){
			document.getElementById("pia-result").style.height="1450px";
		}
		
		if(document.getElementById("mdr-result")!=null){
			//document.getElementById("third-spacing-removepathwaygap").style.display="none";
			document.getElementById("third-spacing").style.width="10px";
		}
		if(document.getElementById("esnp2-result")!=null){
			document.getElementById("pia-result").style.height="1000px";
		}
		if(document.getElementById("comparative-result")!=null){
			document.getElementById("pia-result").style.height="1720px";
		}
		
		// Displaying pathway containers
		if(document.getElementById("full-container-pathway")!=null){
			document.getElementById("full-container-pathway").style.display="";
		}
		if(document.getElementById("half-container-pathway")!=null){
			document.getElementById("half-container-pathway").style.display="";
		}
		if(document.getElementById("third-container-pathway")!=null){
			//document.getElementById("third-container-pathway").style.display="";
		}
		
	}
	else{
		if(document.getElementById("third-container-pathway")!=null){
			document.getElementById("third-container-pathway").style.display="none";
		}
	}
	
}