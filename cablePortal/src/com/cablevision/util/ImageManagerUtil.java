package com.cablevision.util;

import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;

public class ImageManagerUtil extends Component {
	private Image image;

	/**
	 * default Constructor
	 */
	public ImageManagerUtil() {
		image = null;
	}

	/**
	 * Constructor with Image.
	 *
	 * @param image the Image to be displayed .
	 * See the Java glossary under Image for ways to create an Image from a file.
	 */
	public ImageManagerUtil( Image image ) {
		this();
		setImage( image );
	}

	/**
	 * Set or change the current Image to display.
	 * setImage does a MediaTracker to ensure the Image is loaded.
	 * You don't have to.
	 * If you don't plan to use the old image again you should
	 * do a getImage().flush();
	 *
	 * @param image the new Image to be displayed.
	 * If the image jpg may have recently changed, don't use
	 * getImage to create it, use
	 * URL.openConnection()
	 * URLConnection.setUseCaches( false )
	 * Connection.getContent
	 * Component.createImage
	 *
	 */
	public void setImage( Image image ) {
		// even if Image object is same, we use it since it may have changed state.

		this.image = image;

		if ( image != null ) {
			MediaTracker tracker;
			try {
				// wait until image is fully loaded.
				// and so that paint will be instantaneous, rather than gradual as
				// the image arrives.
				// MediaTracker notifies of progress via our Component.ImageObsever interface
				tracker = new MediaTracker( this );
				tracker.addImage( image, 0 );
				tracker.waitForID( 0 );
			} catch ( InterruptedException e ) {
			}
		}
		// image is now ready, let's paint it
		repaint();

	}

	/**
	 * Get the Image currently being displayed.
	 *
	 * @return the Image currently displayed or null if no Image
	 */
	public Image getImage() {
		return image;
	}

}
