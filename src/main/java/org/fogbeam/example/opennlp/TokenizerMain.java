
package org.fogbeam.example.opennlp;


import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

/**
* \brief clase que contiene el main del programa, recibe un input y saca output en fichero
*/
public class TokenizerMain
{
	public static void main( String[] args ) throws Exception
	{
		
		// the provided model
		//InputStream modelIn = new FileInputStream( "models/en-token.bin" );

		
		// the model we trained
		/**
		 * Metemos el modelo entrenado
		 */
		InputStream modelIn = new FileInputStream( "models/en-token.model" );
		
		try
		{
			TokenizerModel model = new TokenizerModel( modelIn );
		
			Tokenizer tokenizer = new TokenizerME(model);
			
			/**
			 * Metemos el archivo
			 */
			String filePath="training_data/Ocmulgee National Monument - Georgia.txt";
			final StringBuilder contentBuilder = new StringBuilder();
			try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8)) 
	        {
	            stream.forEach(s -> contentBuilder.append(s).append("\n"));
	        }
	        catch (IOException e) 
	        {
	            e.printStackTrace();
	        }
			/**
			 * Tokenizamos y pasamos a fichero
			 */
			String result = contentBuilder.toString();
			String[] tokens = tokenizer.tokenize
					(result);
			String out = "";
			for( String token : tokens )
			{
				
				out+=token+"\n";
				System.out.println( token );
			}
			FileOutputStream fos = new FileOutputStream("output.txt");
			DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));
		    outStream.writeUTF(out);
		    outStream.close();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
		finally
		{
			if( modelIn != null )
			{
				try
				{
					modelIn.close();
				}
				catch( IOException e )
				{
				}
			}
		}
		System.out.println( "\n-----\ndone" );
	}
}
