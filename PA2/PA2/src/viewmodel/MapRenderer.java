package viewmodel;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.Map.Cell;
import model.Map.Occupant.Player;
import model.Map.Occupiable.DestTile;
import model.Map.Occupiable.Occupiable;

import java.net.URISyntaxException;

import static viewmodel.Config.LEVEL_EDITOR_TILE_SIZE;

/**
 * Renders maps onto canvases
 */
public class MapRenderer {
    private static Image wall = null;
    private static Image crateOnTile = null;
    private static Image crateOnDest = null;

    private static Image playerOnTile = null;
    private static Image playerOnDest = null;

    private static Image dest = null;
    private static Image tile = null;

    static {
        try {
            wall = new Image(MapRenderer.class.getResource("/assets/images/wall.png").toURI().toString());
            crateOnTile = new Image(MapRenderer.class.getResource("/assets/images/crateOnTile.png").toURI().toString());
            crateOnDest = new Image(MapRenderer.class.getResource("/assets/images/crateOnDest.png").toURI().toString());
            playerOnTile = new Image(MapRenderer.class.getResource("/assets/images/playerOnTile.png").toURI().toString());
            playerOnDest = new Image(MapRenderer.class.getResource("/assets/images/playerOnDest.png").toURI().toString());
            dest = new Image(MapRenderer.class.getResource("/assets/images/dest.png").toURI().toString());
            tile = new Image(MapRenderer.class.getResource("/assets/images/tile.png").toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Render the map onto the canvas. This method can be used in Level Editor
     * <p>
     * Hint: set the canvas height and width as a multiple of the rows and cols
     *
     * @param canvas The canvas to be rendered onto
     * @param map    The map holding the current state of the game
     */
    static void render(Canvas canvas, LevelEditorCanvas.Brush[][] map) {
        //TODO
        int rows=map[0].length; int cols=map.length;
        double canvas_height=cols*Config.LEVEL_EDITOR_TILE_SIZE; double canvas_width=rows*Config.LEVEL_EDITOR_TILE_SIZE;

        canvas.setWidth(canvas_width);
        canvas.setHeight(canvas_height);

        for(int x =0; x< rows; x++){
            for(int y = 0; y < cols; y++){
                int dx = (int)(x*Config.LEVEL_EDITOR_TILE_SIZE);
                int dy = (int)(y*Config.LEVEL_EDITOR_TILE_SIZE);
//if(x>=5){System.out.println("huhhhh????");System.out.println(cols);}
//                if(y>=5){System.out.println("rows problem?????");System.out.println(rows);}
                if(map[y][x]==LevelEditorCanvas.Brush.TILE){canvas.getGraphicsContext2D().drawImage(tile, dx, dy);}
                else if(map[y][x]==LevelEditorCanvas.Brush.PLAYER_ON_TILE){canvas.getGraphicsContext2D().drawImage(playerOnTile, dx, dy);}
                else if(map[y][x]==LevelEditorCanvas.Brush.PLAYER_ON_DEST){canvas.getGraphicsContext2D().drawImage(playerOnDest, dx, dy);}
                else if(map[y][x]==LevelEditorCanvas.Brush.CRATE_ON_TILE){canvas.getGraphicsContext2D().drawImage(crateOnTile, dx, dy);}
                else if(map[y][x]==LevelEditorCanvas.Brush.CRATE_ON_DEST){canvas.getGraphicsContext2D().drawImage(crateOnDest, dx, dy);}
                else if(map[y][x]==LevelEditorCanvas.Brush.WALL){canvas.getGraphicsContext2D().drawImage(wall, dx, dy);}
                else if(map[y][x]==LevelEditorCanvas.Brush.DEST){canvas.getGraphicsContext2D().drawImage(dest, dx, dy);}

            }

        }

    }

    /**
     * Render the map onto the canvas. This method can be used in GamePlayPane and LevelSelectPane
     * <p>
     * Hint: set the canvas height and width as a multiple of the rows and cols
     *
     * @param canvas The canvas to be rendered onto
     * @param map    The map holding the current state of the game
     */
    public static void render(Canvas canvas, Cell[][] map) {
        //TODO
    int rows=map[0].length; int cols=map.length;
    double canvas_height=cols*tile.getHeight(); double canvas_width=rows*tile.getWidth();

    canvas.setWidth(canvas_width);
    canvas.setHeight(canvas_height);

        for(int x =0; x< rows; x++) {
            for (int y = 0; y < cols; y++) {
                int dx = (int)(x*tile.getWidth());
                int dy = (int)(y*tile.getHeight());

//System.out.print(map[y][x]);
        if(map[y][x] instanceof DestTile && ( !((Occupiable)map[y][x]).getOccupant().isPresent() ) ){canvas.getGraphicsContext2D().drawImage(dest, dx, dy);}
        else if(map[y][x] instanceof DestTile && ( ((Occupiable)map[y][x]).getOccupant().isPresent() ) && ( ((Occupiable)map[y][x]).getOccupant().get() instanceof Player ) )
        {canvas.getGraphicsContext2D().drawImage(playerOnDest, dx, dy);}
        else if(map[y][x] instanceof DestTile && ( ((Occupiable)map[y][x]).getOccupant().isPresent() )  )
        {canvas.getGraphicsContext2D().drawImage(crateOnDest, dx, dy);}
        else if(! (map[y][x] instanceof Occupiable) )
        {canvas.getGraphicsContext2D().drawImage(wall, dx, dy);}
        else if( map[y][x] instanceof Occupiable && ( ((Occupiable)map[y][x]).getOccupant().isPresent() ) && ( ((Occupiable)map[y][x]).getOccupant().get() instanceof Player ))
        {canvas.getGraphicsContext2D().drawImage(playerOnTile, dx, dy);}
        else if( map[y][x] instanceof Occupiable && ( ((Occupiable)map[y][x]).getOccupant().isPresent() ) )
        {canvas.getGraphicsContext2D().drawImage(crateOnTile, dx, dy);}
        else if( map[y][x] instanceof Occupiable && ( !((Occupiable)map[y][x]).getOccupant().isPresent() ) )
        {canvas.getGraphicsContext2D().drawImage(tile, dx, dy);}



            }
        }

    }
}
